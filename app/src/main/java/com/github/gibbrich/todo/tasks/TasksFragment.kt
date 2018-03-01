package com.github.gibbrich.todo.tasks


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.model.Task


/**
 * A simple [Fragment] subclass.
 */
class TasksFragment : Fragment(), ITaskClickListener, ITasksContract.View
{
    private lateinit var presenter: ITasksContract.Presenter

    private lateinit var listView: ListView
    private lateinit var adapter: TasksAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        adapter = TasksAdapter(ArrayList(), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)

        listView = root.findViewById(R.id.tasks_list)
        listView.adapter = adapter

        val addTaskButton = activity.findViewById<FloatingActionButton>(R.id.addTaskButton)
        addTaskButton.setOnClickListener { presenter.addNewTask() }

        refreshLayout = root.findViewById(R.id.refresh_layout)
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
        )

        refreshLayout.setOnRefreshListener { presenter.loadTasks() }

        return root
    }

    override fun setPresenter(presenter: ITasksContract.Presenter)
    {
        this.presenter = presenter
    }

    override fun onTaskClick(task: Task)
    {
        presenter.openTaskDetails(task)
    }

    override fun completeTask(task: Task)
    {
        presenter.completeTask(task)
    }

    override fun activateTask(task: Task)
    {
        presenter.activateTask(task)
    }

    override fun setLoadingIndicator(isLoading: Boolean)
    {
        refreshLayout.isRefreshing = isLoading
    }

    companion object
    {
        fun createInstance() = TasksFragment()
    }
}
