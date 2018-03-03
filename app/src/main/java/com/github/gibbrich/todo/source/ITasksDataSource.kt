package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
interface ITasksDataSource
{
    fun getTasks(onTasksLoaded: (List<Task>) -> Unit)
    fun saveTask(task: Task)
    fun getTask(taskGUID: String, onTaskLoaded: (Task) -> Unit)
    fun setTaskState(taskGUID: String, isCompleted: Boolean)
    fun deleteTask(task: Task)
}