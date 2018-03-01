package com.github.gibbrich.todo.addedittask

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.source.ITasksDataSource

/**
 * Created by Артур on 01.03.2018.
 */
class AddEditTaskPresenter(
        private val view: IAddEditTaskContract.View,
        private val dataSource: ITasksDataSource
): IAddEditTaskContract.Presenter
{
    init
    {
        view.setPresenter(this)
    }

    override fun createTask(title: String, description: String)
    {
        // todo - check title and description if they are empty
        dataSource.saveTask(Task(title, description))
        view.returnToTasksList()
    }
}