package com.github.gibbrich.todo.addedittask

import com.github.gibbrich.todo.model.Task
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
            val onTaskLoaded: (Task) -> Unit = {
                if (it.title != null)
                {
                    view.setTitle(it.title)
                }

                if (it.description != null)
                {
                    view.setDescription(it.description)
                }
            }
            dataSource.getTask(taskGUID, onTaskLoaded)
        }
    }
}