package com.github.gibbrich.todo.tasks

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.source.ITasksDataSource

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
class TasksPresenter(
        private val view: ITasksContract.View,
        private val dataSource: ITasksDataSource
): ITasksContract.Presenter
{
    init
    {
        view.setPresenter(this)
    }

    override fun completeTask(task: Task)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(task: Task)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openTaskDetails(task: Task)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addNewTask()
    {
        view.showAddTask()
    }

    override fun loadTasks()
    {
        view.setLoadingIndicator(true)
        val callback: (List<Task>) -> Unit = {
            view.setLoadingIndicator(false)
            view.showTasks(it)
        }
        dataSource.getTasks(callback)
    }
}