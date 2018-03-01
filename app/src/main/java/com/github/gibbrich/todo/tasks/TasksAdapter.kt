package com.github.gibbrich.todo.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.model.Task

/**
 * Created by Dvurechenskiyi on 01.03.2018.
 */
class TasksAdapter(private val tasks: List<Task>, private val listener: ITaskClickListener): BaseAdapter()
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val viewHolder: TaskViewHolder
        var view = convertView

        if (view == null || view.tag == null)
        {
            val layoutInflater = LayoutInflater.from(parent!!.context)
            view = layoutInflater.inflate(R.layout.task_item, parent)

            val completeCheckBox = view.findViewById<CheckBox>(R.id.complete)
            val titleTextView = view.findViewById<TextView>(R.id.title)
            viewHolder = TaskViewHolder(completeCheckBox, titleTextView)
        }
        else
        {
            viewHolder = view.tag as TaskViewHolder
        }

        val task = tasks[position]

        viewHolder.title.text = task.title
        viewHolder.completeCheckBox.isChecked = task.isCompleted
        viewHolder.completeCheckBox.setOnClickListener {
            if (task.isCompleted)
            {
                listener.activateTask(task)
            }
            else
            {
                listener.completeTask(task)
            }
        }

        view!!.setOnClickListener { listener.onTaskClick(task) }

        return view
    }

    override fun getItem(position: Int) = tasks[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = tasks.size
}

class TaskViewHolder(
        val completeCheckBox: CheckBox,
        val title: TextView
)

interface ITaskClickListener
{
    fun onTaskClick(task: Task)
    fun completeTask(task: Task)
    fun activateTask(task: Task)
}