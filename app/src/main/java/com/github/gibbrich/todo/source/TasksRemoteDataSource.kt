package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import com.github.gibbrich.todo.utils.AppExecutors

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

    override fun getTasks(listener: ILoadTasksListener)
    {
        performNetworkRequest {
            val tasks = tasks.values.toList()
            AppExecutors.executeOnMainThread {
                listener.onTasksLoaded(tasks)
            }
        }
    }

    override fun getTask(taskGUID: String, listener: ILoadTaskListener)
    {
        AppExecutors.executeOnNetworkThread {
            Thread.sleep(NETWORK_LATENCY)

            val task = tasks[taskGUID]

            AppExecutors.executeOnMainThread {
                if (task == null)
                {
                    listener.onDataNotAvailable()
                }
                else
                {
                    listener.onTaskLoaded(task)
                }
            }
        }
    }

    override fun saveTask(task: Task)
    {
        performNetworkRequest {
            tasks.put(task.id, task)
        }
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
        performNetworkRequest {
            tasks.remove(task.id)
        }
    }

    override fun deleteAllTasks()
    {
        performNetworkRequest {
            tasks.clear()
        }
    }

    private fun performNetworkRequest(command: () -> Unit)
    {
        AppExecutors.executeOnNetworkThread {
            Thread.sleep(NETWORK_LATENCY)
            command()
        }
    }
}