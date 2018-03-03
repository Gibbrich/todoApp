package com.github.gibbrich.todo.addedittask

import com.github.gibbrich.todo.IBasePresenter
import com.github.gibbrich.todo.IBaseView

/**
 * Created by Артур on 01.03.2018.
 */
interface IAddEditTaskContract
{
    interface Presenter: IBasePresenter
    {
        fun saveTask(title: String, description: String)
    }

    interface View: IBaseView<Presenter>
    {
        fun returnToTasksList()
        fun setTitle(title: String)
        fun setDescription(description: String)
    }
}