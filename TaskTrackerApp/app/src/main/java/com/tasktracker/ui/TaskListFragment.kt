package com.tasktracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasktracker.R
import com.tasktracker.data.Task
import com.tasktracker.viewmodel.TaskViewModel

class TaskListFragment : Fragment() {

    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewEmpty: TextView
    private lateinit var adapter: TaskAdapter
    private var taskType: TaskType = TaskType.ALL

    enum class TaskType {
        ALL, ACTIVE, COMPLETED
    }

    companion object {
        private const val ARG_TASK_TYPE = "task_type"

        fun newInstance(taskType: TaskType): TaskListFragment {
            val fragment = TaskListFragment()
            val args = Bundle()
            args.putSerializable(ARG_TASK_TYPE, taskType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskType = arguments?.getSerializable(ARG_TASK_TYPE) as? TaskType ?: TaskType.ALL
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewTasks)
        textViewEmpty = view.findViewById(R.id.textViewEmpty)

        setupRecyclerView()
        observeTasks()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onTaskClick = { task ->
                (activity as? MainActivity)?.showEditTaskDialog(task)
            },
            onTaskChecked = { task, isChecked ->
                viewModel.toggleCompletion(task.id, isChecked)
            },
            onTaskDelete = { task ->
                (activity as? MainActivity)?.showDeleteConfirmation(task)
            },
            onProgressChanged = { task, progress ->
                viewModel.updateProgress(task.id, progress)
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TaskListFragment.adapter
        }
    }

    private fun observeTasks() {
        val tasksLiveData = when (taskType) {
            TaskType.ALL -> viewModel.allTasks
            TaskType.ACTIVE -> viewModel.activeTasks
            TaskType.COMPLETED -> viewModel.completedTasks
        }

        tasksLiveData.observe(viewLifecycleOwner) { tasks ->
            updateUI(tasks)
        }
    }

    private fun updateUI(tasks: List<Task>) {
        if (tasks.isEmpty()) {
            recyclerView.visibility = View.GONE
            textViewEmpty.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            textViewEmpty.visibility = View.GONE
        }
        adapter.submitList(tasks)
    }
}
