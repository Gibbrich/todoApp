package com.github.gibbrich.todo

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
interface IBaseView<in T: IBasePresenter>
{
    fun setPresenter(presenter: T)
}