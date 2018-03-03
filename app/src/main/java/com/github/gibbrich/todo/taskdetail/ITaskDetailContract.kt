package com.github.gibbrich.todo.taskdetail

import com.github.gibbrich.todo.IBasePresenter
import com.github.gibbrich.todo.IBaseView

/**
 * Created by Артур on 01.03.2018.
 */
interface ITaskDetailContract
{
    interface View: IBaseView<Presenter>
    {
        fun showTitle(title: String)
        fun hideTitle()
        fun showDescription(description: String)
        fun hideDescription()
        fun setTaskCompleted(isCompleted: Boolean)
        fun showEditTask(taskGUID: String)
        fun showTaskDeleted()
    }

    interface Presenter: IBasePresenter
    {
        fun loadTask()
        fun setTaskComplete()
        fun setTaskActive()
        fun editTask()
        fun deleteTask()
    }
}