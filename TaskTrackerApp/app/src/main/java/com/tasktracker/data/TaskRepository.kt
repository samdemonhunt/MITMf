package com.tasktracker.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    val activeTasks: LiveData<List<Task>> = taskDao.getActiveTasks()
    val completedTasks: LiveData<List<Task>> = taskDao.getCompletedTasks()

    fun getTaskById(taskId: Int): LiveData<Task> {
        return taskDao.getTaskById(taskId)
    }

    suspend fun insert(task: Task): Long {
        return taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun updateProgress(taskId: Int, progress: Int) {
        taskDao.updateProgress(taskId, progress, System.currentTimeMillis())
    }

    suspend fun toggleCompletion(taskId: Int, isCompleted: Boolean) {
        taskDao.updateCompletionStatus(taskId, isCompleted, System.currentTimeMillis())
    }
}
