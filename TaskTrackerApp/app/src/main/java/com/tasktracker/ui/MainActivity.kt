package com.tasktracker.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.tasktracker.R
import com.tasktracker.data.Priority
import com.tasktracker.data.Task
import com.tasktracker.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        setupToolbar()
        setupViewPager()
        setupFab()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        val adapter = TaskPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.all_tasks)
                1 -> getString(R.string.active_tasks)
                2 -> getString(R.string.completed_tasks)
                else -> ""
            }
        }.attach()
    }

    private fun setupFab() {
        fab = findViewById(R.id.fabAddTask)
        fab.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null)
        val editTextTitle = dialogView.findViewById<TextInputEditText>(R.id.editTextTitle)
        val editTextDescription = dialogView.findViewById<TextInputEditText>(R.id.editTextDescription)
        val radioGroupPriority = dialogView.findViewById<RadioGroup>(R.id.radioGroupPriority)
        val seekBarProgress = dialogView.findViewById<SeekBar>(R.id.seekBarProgress)
        val textViewProgressValue = dialogView.findViewById<TextView>(R.id.textViewProgressValue)

        seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewProgressValue.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        AlertDialog.Builder(this)
            .setTitle(R.string.add_task)
            .setView(dialogView)
            .setPositiveButton(R.string.save) { _, _ ->
                val title = editTextTitle.text.toString().trim()
                if (title.isEmpty()) {
                    editTextTitle.error = getString(R.string.task_title_required)
                    return@setPositiveButton
                }

                val description = editTextDescription.text.toString().trim()
                val progress = seekBarProgress.progress
                val priority = when (radioGroupPriority.checkedRadioButtonId) {
                    R.id.radioLow -> Priority.LOW
                    R.id.radioMedium -> Priority.MEDIUM
                    R.id.radioHigh -> Priority.HIGH
                    R.id.radioUrgent -> Priority.URGENT
                    else -> Priority.MEDIUM
                }

                val task = Task(
                    title = title,
                    description = description,
                    progress = progress,
                    priority = priority
                )

                viewModel.insertTask(task)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    fun showEditTaskDialog(task: Task) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null)
        val editTextTitle = dialogView.findViewById<TextInputEditText>(R.id.editTextTitle)
        val editTextDescription = dialogView.findViewById<TextInputEditText>(R.id.editTextDescription)
        val radioGroupPriority = dialogView.findViewById<RadioGroup>(R.id.radioGroupPriority)
        val seekBarProgress = dialogView.findViewById<SeekBar>(R.id.seekBarProgress)
        val textViewProgressValue = dialogView.findViewById<TextView>(R.id.textViewProgressValue)

        // Pre-fill with existing task data
        editTextTitle.setText(task.title)
        editTextDescription.setText(task.description)
        seekBarProgress.progress = task.progress
        textViewProgressValue.text = "${task.progress}%"

        when (task.priority) {
            Priority.LOW -> radioGroupPriority.check(R.id.radioLow)
            Priority.MEDIUM -> radioGroupPriority.check(R.id.radioMedium)
            Priority.HIGH -> radioGroupPriority.check(R.id.radioHigh)
            Priority.URGENT -> radioGroupPriority.check(R.id.radioUrgent)
        }

        seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewProgressValue.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        AlertDialog.Builder(this)
            .setTitle(R.string.edit_task)
            .setView(dialogView)
            .setPositiveButton(R.string.save) { _, _ ->
                val title = editTextTitle.text.toString().trim()
                if (title.isEmpty()) {
                    editTextTitle.error = getString(R.string.task_title_required)
                    return@setPositiveButton
                }

                val description = editTextDescription.text.toString().trim()
                val progress = seekBarProgress.progress
                val priority = when (radioGroupPriority.checkedRadioButtonId) {
                    R.id.radioLow -> Priority.LOW
                    R.id.radioMedium -> Priority.MEDIUM
                    R.id.radioHigh -> Priority.HIGH
                    R.id.radioUrgent -> Priority.URGENT
                    else -> Priority.MEDIUM
                }

                val updatedTask = task.copy(
                    title = title,
                    description = description,
                    progress = progress,
                    priority = priority,
                    updatedAt = System.currentTimeMillis()
                )

                viewModel.updateTask(updatedTask)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    fun showDeleteConfirmation(task: Task) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_task_title)
            .setMessage(R.string.delete_task_message)
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteTask(task)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private inner class TaskPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TaskListFragment.newInstance(TaskListFragment.TaskType.ALL)
                1 -> TaskListFragment.newInstance(TaskListFragment.TaskType.ACTIVE)
                2 -> TaskListFragment.newInstance(TaskListFragment.TaskType.COMPLETED)
                else -> TaskListFragment.newInstance(TaskListFragment.TaskType.ALL)
            }
        }
    }
}
