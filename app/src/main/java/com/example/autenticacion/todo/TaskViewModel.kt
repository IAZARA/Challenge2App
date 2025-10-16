package com.example.autenticacion.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).taskDao()
    private val repo = TaskRepository(dao)

    val pending = repo.pending
    val completed = repo.completed

    fun getById(id: Int) = repo.getById(id)

    fun addTask(title: String, description: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.add(title, description)
        }
    }

    fun toggleCompletion(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(task.copy(completed = !task.completed))
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) { repo.update(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) { repo.delete(task) }
    }
}

