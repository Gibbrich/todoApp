package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.utils.AppExecutors

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
object TasksLocalDataSource: ITasksDataSource
{
    override fun getTasks(onTasksLoaded: (List<Task>) -> Unit)
    {
        AppExecutors.executeOnDiskThread {
            val tasks = ToDoDatabase.instance.dao.getTasks()
            Thread.sleep(3000)
            AppExecutors.executeOnMainThread { onTasksLoaded(tasks) }
        }
    }

}