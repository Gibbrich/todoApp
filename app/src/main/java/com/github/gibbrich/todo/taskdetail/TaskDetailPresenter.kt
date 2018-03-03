package com.github.gibbrich.todo.taskdetail

import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.ToDoApplication
import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.source.ITasksDataSource

/**
 * Created by Dvurechenskiyi on 02.03.2018.
 */
class TaskDetailPresenter(
        private val taskGUID: String,
        private val view: ITaskDetailContract.View,
        private val dataSource: ITasksDataSource
): ITaskDetailContract.Presenter
{
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
        val onTaskLoaded: (Task) -> Unit = {
            if (it.title == null)
            {
                view.hideTitle()
            }
            else
            {
                view.showTitle(it.title)
            }

            if (it.description == null)
            {
                view.hideDescription()
            }
            else
            {
                view.showDescription(it.description)
            }

            view.setTaskCompleted(it.isCompleted)
        }
        dataSource.getTask(taskGUID, onTaskLoaded)
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
}