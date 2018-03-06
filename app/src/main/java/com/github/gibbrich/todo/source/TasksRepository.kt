package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.utils.logEnd
import com.github.gibbrich.todo.utils.logStart
import io.reactivex.Flowable
import java.util.HashMap

/**
 * Created by Dvurechenskiyi on 02.03.2018.
 */
object TasksRepository: ITasksDataSource
{
    private val localDataSource: ITasksDataSource
    private val remoteDataSource: ITasksDataSource

    private var cachedTasks: MutableMap<String, Task> = LinkedHashMap()
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

    override fun getTasks(): Flowable<List<Task>>
    {
        if (!cacheIsDirty)
        {
            return Flowable
                    .fromIterable(cachedTasks.values)
                    .toList()
                    .toFlowable()
        }
        else
        {
            val remoteTasks = remoteDataSource
                    .getTasks()
                    .flatMap { saveRemoteTasks(it) }
                    .doOnComplete { cacheIsDirty = false }

            val localTasks = localDataSource
                    .getTasks()
                    .flatMap { tasks ->
                        Flowable
                                .fromIterable(tasks)
                                .doOnNext { task -> cachedTasks.put(task.id, task) }
                                .toList()
                                .toFlowable()
                    }

            return Flowable
                    .concat(localTasks, remoteTasks)
                    .firstOrError()
                    .toFlowable()
        }
    }

    private fun saveRemoteTasks(tasks: List<Task>): Flowable<List<Task>>
    {
        return Flowable
                .fromIterable(tasks)
                .doOnNext {
                    localDataSource.saveTask(it)
                    cachedTasks.put(it.id, it)
                }
                .toList()
                .toFlowable()
    }

    override fun getTask(taskGUID: String): Flowable<Task?>
    {
        if (cachedTasks.containsKey(taskGUID))
        {
            return Flowable.just(cachedTasks[taskGUID]!!)
        }

        val localTask = localDataSource
                .getTask(taskGUID)
                .doOnNext {
                    if (it != null)
                    {
                        cachedTasks.put(it.id, it)
                    }
                }

        val remoteTask = remoteDataSource
                .getTask(taskGUID)
                .doOnNext {
                    if (it != null)
                    {
                        localDataSource.saveTask(it)
                        cachedTasks.put(it.id, it)
                    }
                }

        return Flowable
                .concat(localTask, remoteTask)
                .firstElement()
                .toFlowable()
    }

    override fun setTaskState(taskGUID: String, isCompleted: Boolean)
    {
        remoteDataSource.setTaskState(taskGUID, isCompleted)
        localDataSource.setTaskState(taskGUID, isCompleted)

        val task = cachedTasks[taskGUID]!!
        val newTask = Task(task.id, task.title, task.description, isCompleted)
        cachedTasks.put(taskGUID, newTask)
    }

    override fun deleteTask(task: Task)
    {
        remoteDataSource.deleteTask(task)
        localDataSource.deleteTask(task)
        cachedTasks.remove(task.id)
    }

    override fun deleteAllTasks()
    {
        remoteDataSource.deleteAllTasks()
        localDataSource.deleteAllTasks()
        cachedTasks.clear()
    }
}