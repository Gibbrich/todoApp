package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import java.util.HashMap

/**
 * Created by Dvurechenskiyi on 02.03.2018.
 */
object TasksRepository: ITasksDataSource
{
    private val localDataSource: ITasksDataSource
    private val remoteDataSource: ITasksDataSource

    private var cachedTasks: MutableMap<String, Task> = HashMap()
    private var cacheIsDirty = true

    init
    {
        // todo switch to DI
        localDataSource = TasksLocalDataSource
        remoteDataSource = TasksRemoteDataSource
    }

    override fun saveTask(task: Task)
    {
        cachedTasks.put(task.id, task)
        localDataSource.saveTask(task)
        remoteDataSource.saveTask(task)
    }

    override fun getTasks(listener: ILoadTasksListener)
    {
        if (!cacheIsDirty)
        {
            listener.onTasksLoaded(cachedTasks.values.toList())
        }
        else
        {
            val localTasksLoadListener: ILoadTasksListener = object : ILoadTasksListener
            {
                override fun onTasksLoaded(tasks: List<Task>)
                {
                    refreshCache(tasks)
                    listener.onTasksLoaded(tasks)
                }

                override fun onDataNotAvailable()
                {
                    listener.onDataNotAvailable()
                }
            }

            val remoteTasksLoadListener: ILoadTasksListener = object : ILoadTasksListener
            {
                override fun onTasksLoaded(tasks: List<Task>)
                {
                    refreshCache(tasks)
                    refreshLocalDataSource(tasks)
                    listener.onTasksLoaded(tasks)
                }

                override fun onDataNotAvailable()
                {
                    localDataSource.getTasks(localTasksLoadListener)
                }
            }

            remoteDataSource.getTasks(remoteTasksLoadListener)
        }
    }

    override fun getTask(taskGUID: String, listener: ILoadTaskListener)
    {
        if (cachedTasks.containsKey(taskGUID))
        {
            listener.onTaskLoaded(cachedTasks[taskGUID]!!)
        }
        else
        {
            val remoteTaskLoadListener: ILoadTaskListener = object : ILoadTaskListener
            {
                override fun onTaskLoaded(task: Task)
                {
                    cachedTasks.put(task.id, task)
                    localDataSource.saveTask(task)
                    listener.onTaskLoaded(task)
                }

                override fun onDataNotAvailable()
                {
                    listener.onDataNotAvailable()
                }
            }

            val localTaskLoadListener: ILoadTaskListener = object : ILoadTaskListener
            {
                override fun onTaskLoaded(task: Task)
                {
                    cachedTasks.put(task.id, task)
                    listener.onTaskLoaded(task)
                }

                override fun onDataNotAvailable()
                {
                    remoteDataSource.getTask(taskGUID, remoteTaskLoadListener)
                }
            }

            localDataSource.getTask(taskGUID, localTaskLoadListener)
        }
    }

    override fun setTaskState(taskGUID: String, isCompleted: Boolean)
    {
        remoteDataSource.setTaskState(taskGUID, isCompleted)
        localDataSource.setTaskState(taskGUID, isCompleted)

        val task = cachedTasks.get(taskGUID)!!
        val newTask = Task(task.id, task.title, task.description, isCompleted)
        cachedTasks.put(taskGUID, newTask)
    }

    override fun deleteTask(task: Task)
    {
        remoteDataSource.deleteTask(task)
        localDataSource.deleteTask(task)
        cachedTasks.remove(task.id)
    }

    private fun refreshCache(tasks: List<Task>)
    {
        cachedTasks.clear()
        tasks.forEach { cachedTasks.put(it.id, it) }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(tasks: List<Task>)
    {
        localDataSource.deleteAllTasks()
        tasks.forEach { localDataSource.saveTask(it) }
    }

    override fun deleteAllTasks()
    {
        remoteDataSource.deleteAllTasks()
        localDataSource.deleteAllTasks()
        cachedTasks.clear()
    }
}