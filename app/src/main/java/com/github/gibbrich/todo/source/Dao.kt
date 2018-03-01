package com.github.gibbrich.todo.source

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.github.gibbrich.todo.model.Task

/**
 * Created by Dvurechenskiyi on 27.02.2018.
 */
@Dao
interface Dao
{
    @Query("SELECT entryid, title, description, completed FROM Tasks")
    fun getTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)
}