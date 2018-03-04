package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
interface ITasksDataSource
{
    fun getTasks(listener: ILoadTasksListener)
    fun saveTask(task: Task)
    fun getTask(taskGUID: String, listener: ILoadTaskListener)
    fun setTaskState(taskGUID: String, isCompleted: Boolean)
    fun deleteTask(task: Task)
    fun deleteAllTasks()
}

interface ILoadTasksListener
{
    fun onTasksLoaded(tasks: List<Task>)
    fun onDataNotAvailable()
}

interface ILoadTaskListener
{
    fun onTaskLoaded(task: Task)
    fun onDataNotAvailable()
}