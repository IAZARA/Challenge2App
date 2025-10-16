package com.example.autenticacion.todo

class TaskRepository(private val dao: TaskDao) {
    val pending = dao.getPending()
    val completed = dao.getCompleted()

    fun getById(id: Int) = dao.getById(id)

    suspend fun add(title: String, description: String?) {
        dao.insert(Task(title = title, description = description))
    }

    suspend fun update(task: Task) {
        dao.update(task)
    }

    suspend fun delete(task: Task) {
        dao.delete(task)
    }
}

