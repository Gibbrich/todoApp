package com.github.gibbrich.todo.taskdetail

import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.ToDoApplication
import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.source.ILoadTaskListener
import com.github.gibbrich.todo.source.ITasksDataSource

/**
 * Created by Dvurechenskiyi on 02.03.2018.
 */
class TaskDetailPresenter(
        private val taskGUID: String,
        private val view: ITaskDetailContract.View,
        private val dataSource: ITasksDataSource
) : ITaskDetailContract.Presenter
{
    private lateinit var task: Task

    init
    {
        view.setPresenter(this)
    }

    override fun start()
    {
        val loadingLabel = "${ToDoApplication.instance.getString(R.string.loading)}..."
        view.showTitle(loadingLabel)
        view.showDescription(loadingLabel)
        loadTask()
    }

    override fun loadTask()
    {
        val callback: ILoadTaskListener = object : ILoadTaskListener
        {
            override fun onTaskLoaded(task: Task)
            {
                this@TaskDetailPresenter.task = task

                if (task.title == null)
                {
                    view.hideTitle()
                }
                else
                {
                    view.showTitle(task.title)
                }

                if (task.description == null)
                {
                    view.hideDescription()
                }
                else
                {
                    view.showDescription(task.description)
                }

                view.setTaskCompleted(task.isCompleted)
            }

            override fun onDataNotAvailable()
            {
                view.showNoTaskData()
            }
        }

        dataSource.getTask(taskGUID, callback)
    }

    override fun setTaskComplete()
    {
        dataSource.setTaskState(taskGUID, true)
    }

    override fun setTaskActive()
    {
        dataSource.setTaskState(taskGUID, false)
    }

    override fun editTask()
    {
        view.showEditTask(taskGUID)
    }

    override fun deleteTask()
    {
        dataSource.deleteTask(task)
        view.showTaskDeleted()
    }
}