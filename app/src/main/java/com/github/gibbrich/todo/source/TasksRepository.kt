package com.github.gibbrich.todo.source

import com.github.gibbrich.todo.model.Task
import java.util.HashMap

/**
 * Created by Dvurechenskiyi on 02.03.2018.
 */
object TasksRepository: ITasksDataSource
{
    private val cashedTasks: MutableMap<String, Task> = HashMap()
    private var cashIsDirty = true

    override fun getTasks(onTasksLoaded: (List<Task>) -> Unit)
    {
        if (!cashIsDirty)
        {
            onTasksLoaded(cashedTasks.values.toList())
        }

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTask(task: Task)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTask(taskGUID: String, onTaskLoaded: (Task) -> Unit)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTaskState(taskGUID: String, isCompleted: Boolean)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}