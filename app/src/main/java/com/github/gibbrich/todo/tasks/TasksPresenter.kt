package com.github.gibbrich.todo.tasks

import com.github.gibbrich.todo.model.Task

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
class TasksPresenter(view: ITasksContract.View): ITasksContract.Presenter
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadTasks()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}