package com.github.gibbrich.todo.tasks

import android.app.Activity
import com.github.gibbrich.todo.addedittask.AddEditTaskActivity
import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.source.ILoadTasksListener
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
        dataSource.setTaskState(task.id, true)
    }

    override fun activateTask(task: Task)
    {
        dataSource.setTaskState(task.id, false)
    }

    override fun openTaskDetails(task: Task)
    {
        view.showTaskDetails(task.id)
    }

    override fun addNewTask()
    {
        view.showAddTask()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int)
    {
        if (requestCode == AddEditTaskActivity.REQUEST_ADD_TASK && resultCode == Activity.RESULT_OK)
        {
            view.showSuccessfullySavedMessage()
        }
    }

    override fun loadTasks()
    {
        view.setLoadingIndicator(true)

        val callback: ILoadTasksListener = object : ILoadTasksListener
        {
            override fun onTasksLoaded(tasks: List<Task>)
            {
                view.setLoadingIndicator(false)
                view.showTasks(tasks)
            }

            override fun onDataNotAvailable()
            {
                view.setLoadingIndicator(false)
                view.showLoadingTasksError()
            }
        }

        dataSource.getTasks(callback)
    }

    override fun start()
    {
        loadTasks()
    }
}