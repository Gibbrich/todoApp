package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.utils.AppExecutors

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
object TasksLocalDataSource: ITasksDataSource
{
    override fun deleteTask(task: Task)
    {
        AppExecutors.executeOnDiskThread {
            ToDoDatabase.instance.dao.deleteTask(task)
        }
    }


    override fun setTaskState(taskGUID: String, isCompleted: Boolean)
    {
        AppExecutors.executeOnDiskThread {
            ToDoDatabase.instance.dao.setTaskState(taskGUID, isCompleted)
        }
    }

    override fun getTask(taskGUID: String, onTaskLoaded: (Task) -> Unit)
    {
        AppExecutors.executeOnDiskThread {
            val task = ToDoDatabase.instance.dao.getTask(taskGUID)
            AppExecutors.executeOnMainThread { onTaskLoaded(task) }
        }
    }

    override fun saveTask(task: Task)
    {
        AppExecutors.executeOnDiskThread {
            ToDoDatabase.instance.dao.insertTask(task)
        }
    }

    override fun getTasks(onTasksLoaded: (List<Task>) -> Unit)
    {
        AppExecutors.executeOnDiskThread {
            val tasks = ToDoDatabase.instance.dao.getTasks()
            AppExecutors.executeOnMainThread { onTasksLoaded(tasks) }
        }
    }

}