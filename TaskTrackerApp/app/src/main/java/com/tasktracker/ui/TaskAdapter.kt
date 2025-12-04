package com.tasktracker.ui

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.tasktracker.R
import com.tasktracker.data.Priority
import com.tasktracker.data.Task

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onTaskChecked: (Task, Boolean) -> Unit,
    private val onTaskDelete: (Task) -> Unit,
    private val onProgressChanged: (Task, Int) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBoxComplete: CheckBox = itemView.findViewById(R.id.checkBoxComplete)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewPriority: TextView = itemView.findViewById(R.id.textViewPriority)
        private val progressBar: LinearProgressIndicator = itemView.findViewById(R.id.progressBar)
        private val textViewProgress: TextView = itemView.findViewById(R.id.textViewProgress)
        private val buttonDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)

        fun bind(task: Task) {
            textViewTitle.text = task.title
            textViewDescription.text = task.description
            textViewDescription.visibility = if (task.description.isBlank()) View.GONE else View.VISIBLE

            checkBoxComplete.isChecked = task.isCompleted

            // Apply strikethrough if completed
            if (task.isCompleted) {
                textViewTitle.paintFlags = textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textViewTitle.paintFlags = textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Set priority
            textViewPriority.text = task.priority.name
            textViewPriority.setTextColor(getPriorityColor(task.priority))

            // Set progress
            progressBar.progress = task.progress
            textViewProgress.text = "${task.progress}%"

            // Click listeners
            itemView.setOnClickListener { onTaskClick(task) }

            checkBoxComplete.setOnCheckedChangeListener { _, isChecked ->
                onTaskChecked(task, isChecked)
            }

            buttonDelete.setOnClickListener { onTaskDelete(task) }
        }

        private fun getPriorityColor(priority: Priority): Int {
            return when (priority) {
                Priority.LOW -> Color.parseColor("#4CAF50")
                Priority.MEDIUM -> Color.parseColor("#2196F3")
                Priority.HIGH -> Color.parseColor("#FF9800")
                Priority.URGENT -> Color.parseColor("#F44336")
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
