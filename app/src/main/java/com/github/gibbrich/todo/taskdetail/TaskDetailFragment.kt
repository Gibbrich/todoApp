package com.github.gibbrich.todo.taskdetail


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.addedittask.AddEditTaskActivity


/**
 * A simple [Fragment] subclass.
 */
class TaskDetailFragment : Fragment(), ITaskDetailContract.View
{
    private lateinit var presenter: ITaskDetailContract.Presenter

    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var isCompleted: CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_task_detail, container, false)

        setHasOptionsMenu(true)

        title = root.findViewById(R.id.task_detail_title)
        description = root.findViewById(R.id.task_detail_description)

        isCompleted = root.findViewById(R.id.task_detail_complete)
        isCompleted.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                presenter.setTaskComplete()
            }
            else
            {
                presenter.setTaskActive()
            }
        }

        activity.findViewById<FloatingActionButton>(R.id.fab_edit_task)
                .setOnClickListener { presenter.editTask() }

        return root
    }

    override fun onResume()
    {
        super.onResume()

        presenter.subscribe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AddEditTaskActivity.REQUEST_EDIT_TASK && resultCode == Activity.RESULT_OK)
        {
            activity.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.task_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId)
        {
            R.id.menu_delete -> {
                presenter.deleteTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setPresenter(presenter: ITaskDetailContract.Presenter)
    {
        this.presenter = presenter
    }

    override fun showTitle(title: String)
    {
        this.title.text = title
        this.title.visibility = View.VISIBLE
    }

    override fun hideTitle()
    {
        this.title.visibility = View.GONE
    }

    override fun showDescription(description: String)
    {
        this.description.text = description
        this.description.visibility = View.VISIBLE
    }

    override fun hideDescription()
    {
        this.description.visibility = View.GONE
    }

    override fun setTaskCompleted(isCompleted: Boolean)
    {
        this.isCompleted.isChecked = isCompleted
    }

    override fun showEditTask(taskGUID: String)
    {
        val intent = AddEditTaskActivity.getIntent(activity, taskGUID)
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_EDIT_TASK)
    }

    override fun showTaskDeleted()
    {
        activity.finish()
    }

    override fun showNoTaskData()
    {
        hideTitle()
        hideDescription()
        Toast.makeText(activity, "No data available", Toast.LENGTH_SHORT).show()
    }

    companion object
    {
        fun getInstance() = TaskDetailFragment()
    }
}