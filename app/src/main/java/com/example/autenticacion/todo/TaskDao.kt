package com.example.autenticacion.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE completed = 0 ORDER BY createdAt DESC")
    fun getPending(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE completed = 1 ORDER BY createdAt DESC")
    fun getCompleted(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Int): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}

