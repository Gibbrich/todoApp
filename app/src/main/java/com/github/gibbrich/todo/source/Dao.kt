package com.github.gibbrich.todo.source

import android.arch.persistence.room.*
import android.arch.persistence.room.Dao
import com.github.gibbrich.todo.model.Task
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by Dvurechenskiyi on 27.02.2018.
 */
@Dao
interface Dao
{
    @Query("SELECT entryid, title, description, completed FROM Tasks")
    fun getTasks(): Flowable<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Query("SELECT entryid, title, description, completed FROM Tasks WHERE entryid = :taskGUID")
    fun getTask(taskGUID: String): Maybe<Task>

    @Query("UPDATE Tasks SET completed = :isCompleted WHERE entryid = :taskGUID")
    fun setTaskState(taskGUID: String, isCompleted: Boolean)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM Tasks")
    fun deleteAllTasks()
}