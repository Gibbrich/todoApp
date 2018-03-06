package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import io.reactivex.Flowable

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
interface ITasksDataSource
{
    fun getTasks(): Flowable<List<Task>>
    fun saveTask(task: Task)
    fun getTask(taskGUID: String): Flowable<Task?>
    fun setTaskState(taskGUID: String, isCompleted: Boolean)
    fun deleteTask(task: Task)
    fun deleteAllTasks()
}