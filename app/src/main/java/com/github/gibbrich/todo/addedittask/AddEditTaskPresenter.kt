package com.github.gibbrich.todo.addedittask

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.source.ILoadTaskListener
import com.github.gibbrich.todo.source.ITasksDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Артур on 01.03.2018.
 */
class AddEditTaskPresenter(
        private val taskGUID: String?,
        private val view: IAddEditTaskContract.View,
        private val dataSource: ITasksDataSource
): IAddEditTaskContract.Presenter
{
    private val disposables: CompositeDisposable = CompositeDisposable()

    init
    {
        view.setPresenter(this)
    }

    override fun saveTask(title: String, description: String)
    {
        if (taskGUID == null)
        {
            // todo - check title and description if they are empty
            dataSource.saveTask(Task(title, description))
            view.returnToTasksList()
        }
        else
        {
            val task = Task(taskGUID, title, description)
            dataSource.saveTask(task)
            view.returnToTasksList()
        }
    }

    override fun subscribe()
    {
        if (taskGUID != null)
        {
//            val callback: ILoadTaskListener = object : ILoadTaskListener
//            {
//                override fun onTaskLoaded(task: Task)
//                {
//                    if (task.title != null)
//                    {
//                        view.setTitle(task.title)
//                    }
//
//                    if (task.description != null)
//                    {
//                        view.setDescription(task.description)
//                    }
//                }
//
//                override fun onDataNotAvailable()
//                {
//                }
//            }
//
//            dataSource.getTask(taskGUID, callback)

            val disposable = dataSource
                    .getTask(taskGUID)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { task ->
                                if (task != null)
                                {
                                    if (task.title != null)
                                    {
                                        view.setTitle(task.title)
                                    }

                                    if (task.description != null)
                                    {
                                        view.setDescription(task.description)
                                    }
                                }
                            }
                    )

            disposables.add(disposable)
        }
    }

    override fun unsubscribe()
    {
        disposables.clear()
    }
}