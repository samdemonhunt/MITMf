package com.tasktracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tasktracker.data.Task
import com.tasktracker.data.TaskDatabase
import com.tasktracker.data.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    val activeTasks: LiveData<List<Task>>
    val completedTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
        activeTasks = repository.activeTasks
        completedTasks = repository.completedTasks
    }

    fun getTaskById(taskId: Int): LiveData<Task> {
        return repository.getTaskById(taskId)
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun updateProgress(taskId: Int, progress: Int) = viewModelScope.launch {
        repository.updateProgress(taskId, progress)
    }

    fun toggleCompletion(taskId: Int, isCompleted: Boolean) = viewModelScope.launch {
        repository.toggleCompletion(taskId, isCompleted)
    }
}
