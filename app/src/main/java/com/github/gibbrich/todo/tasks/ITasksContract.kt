package com.github.gibbrich.todo.tasks

import com.github.gibbrich.todo.IBasePresenter
import com.github.gibbrich.todo.IBaseView
import com.github.gibbrich.todo.model.Task

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
interface ITasksContract
{
    interface Presenter: IBasePresenter
    {
        fun openTaskDetails(task: Task)
        fun completeTask(task: Task)
        fun activateTask(task: Task)
        fun addNewTask()
        fun loadTasks()
        fun onActivityResult(requestCode: Int, resultCode: Int)
    }

    interface View: IBaseView<Presenter>
    {
        fun setLoadingIndicator(isLoading: Boolean)
        fun showTasks(tasks: List<Task>)
        fun showAddTask()
        fun showSuccessfullySavedMessage()
        fun showTaskDetails(taskGUID: String)
        fun showLoadingTasksError()
    }
}