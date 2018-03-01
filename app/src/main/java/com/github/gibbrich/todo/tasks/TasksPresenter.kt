package com.github.gibbrich.todo.tasks

import android.app.Activity
import com.github.gibbrich.todo.addedittask.AddEditTaskActivity
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
        view.showStub()
    }

    override fun activateTask(task: Task)
    {
        view.showStub()
    }

    override fun openTaskDetails(task: Task)
    {
        view.showStub()
    }

    override fun addNewTask()
    {
        view.showAddTask()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int)
    {
        if (requestCode == AddEditTaskActivity.REQUEST_ADD_EDIT_TASK && resultCode == Activity.RESULT_OK)
        {
            view.showSuccessfullySavedMessage()
        }
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