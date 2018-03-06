package com.github.gibbrich.todo.source

import android.util.Log
import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.utils.AppExecutors
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import org.intellij.lang.annotations.Flow
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Артур on 04.03.2018.
 */
object TasksRemoteDataSource: ITasksDataSource
{
    private const val NETWORK_LATENCY = 3000L
    val tasks: MutableMap<String, Task> = HashMap()

    init
    {
        val task1 = Task("Build tower in Pisa", "Ground looks good, no foundation work required.")
        val task2 = Task("Finish bridge in Tacoma", "Found awesome girders at half the cost!")

        tasks.put(task1.id, task1)
        tasks.put(task2.id, task2)
    }

    override fun getTasks(): Flowable<List<Task>>
    {
        return Flowable
                .fromIterable(tasks.values)
                .toList()
                .toFlowable()
                .delay(NETWORK_LATENCY, TimeUnit.MILLISECONDS)
    }

    override fun getTask(taskGUID: String): Flowable<Task?>
    {
        if (tasks[taskGUID] == null)
        {
            return Flowable
                    .empty<Task>()
                    .delay(NETWORK_LATENCY, TimeUnit.MILLISECONDS)
        }
        else
        {
            return Flowable
                    .just(tasks[taskGUID])
                    .delay(NETWORK_LATENCY, TimeUnit.MILLISECONDS)
        }
    }

    override fun saveTask(task: Task)
    {
        performNetworkRequest { tasks.put(task.id, task) }
    }

    override fun setTaskState(taskGUID: String, isCompleted: Boolean)
    {
        performNetworkRequest {
            val task = tasks[taskGUID]!!
            val newTask = Task(task.id, task.title, task.description, isCompleted)
            tasks.put(newTask.id, newTask)
        }
    }

    override fun deleteTask(task: Task)
    {
        performNetworkRequest { tasks.remove(task.id) }
    }

    override fun deleteAllTasks()
    {
        performNetworkRequest { tasks.clear() }
    }

    private fun performNetworkRequest(command: () -> Unit)
    {
        Completable
                .fromAction(command)
                .delay(NETWORK_LATENCY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .subscribe()
    }
}