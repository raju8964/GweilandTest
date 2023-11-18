package com.cord.simpletest.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cord.simpletest.ui.components.chatscreen.model.Task


@Dao
interface MyDao {

    @Query("SELECT * FROM task")
    fun getAll(): LiveData<List<Task>>?

    @Insert
    suspend fun insert(task: Task?)

    @Delete
    suspend fun delete(task: Task?)

    @Update
    suspend fun update(task: Task?)
}