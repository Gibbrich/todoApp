package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.utils.AppExecutors
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
object TasksLocalDataSource : ITasksDataSource
{
    override fun getTasks() = ToDoDatabase.instance.dao.getTasks()

    override fun getTask(taskGUID: String) = ToDoDatabase.instance.dao.getTask(taskGUID).toFlowable()

    override fun deleteAllTasks()
    {
        performAction { ToDoDatabase.instance.dao.deleteAllTasks() }
    }

    override fun deleteTask(task: Task)
    {
        performAction { ToDoDatabase.instance.dao.deleteTask(task) }
    }

    override fun setTaskState(taskGUID: String, isCompleted: Boolean)
    {
        performAction { ToDoDatabase.instance.dao.setTaskState(taskGUID, isCompleted) }
    }

    override fun saveTask(task: Task)
    {
        performAction { ToDoDatabase.instance.dao.insertTask(task) }
    }

    private fun performAction(action: () -> Unit)
    {
        Completable
                .fromAction(action)
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}