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
    }

    interface Presenter: IBasePresenter
    {
    }
}