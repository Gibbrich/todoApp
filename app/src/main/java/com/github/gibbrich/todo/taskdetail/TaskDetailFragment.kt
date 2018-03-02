package com.github.gibbrich.todo.taskdetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

import com.github.gibbrich.todo.R


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

        title = root.findViewById(R.id.task_detail_title)
        description = root.findViewById(R.id.task_detail_description)
        isCompleted = root.findViewById(R.id.task_detail_complete)

        return root
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
        this.isCompleted.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                presenter.setTaskComplete()
            }
            else
            {
                presenter.setTaskActive()
            }
        }
    }

    companion object
    {
        fun getInstance() = TaskDetailFragment()
    }
}
