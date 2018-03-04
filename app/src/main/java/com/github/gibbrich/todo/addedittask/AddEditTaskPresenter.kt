package com.github.gibbrich.todo.addedittask

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.source.ILoadTaskListener
import com.github.gibbrich.todo.source.ITasksDataSource

/**
 * Created by Артур on 01.03.2018.
 */
class AddEditTaskPresenter(
        private val taskGUID: String?,
        private val view: IAddEditTaskContract.View,
        private val dataSource: ITasksDataSource
): IAddEditTaskContract.Presenter
{
    init
    {
        view.setPresenter(this)
    }

    override fun saveTask(title: String, description: String)
    {
        if (taskGUID == null)
        {
            // todo - check title and description if they are empty
            dataSource.saveTask(Task(title, description))
            view.returnToTasksList()
        }
        else
        {
            val task = Task(taskGUID, title, description)
            dataSource.saveTask(task)
            view.returnToTasksList()
        }
    }

    override fun start()
    {
        if (taskGUID != null)
        {
            val callback: ILoadTaskListener = object : ILoadTaskListener
            {
                override fun onTaskLoaded(task: Task)
                {
                    if (task.title != null)
                    {
                        view.setTitle(task.title)
                    }

                    if (task.description != null)
                    {
                        view.setDescription(task.description)
                    }
                }

                override fun onDataNotAvailable()
                {
                }
            }

            dataSource.getTask(taskGUID, callback)
        }
    }
}