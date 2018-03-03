package com.github.gibbrich.todo.addedittask


import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.github.gibbrich.todo.R


/**
 * A simple [Fragment] subclass.
 */
class AddEditTaskFragment : Fragment(), IAddEditTaskContract.View
{
    private lateinit var presenter: IAddEditTaskContract.Presenter

    private lateinit var title: EditText
    private lateinit var description: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_edit_task, container, false)

        title = root.findViewById(R.id.add_task_title)
        description = root.findViewById(R.id.add_task_description)

        val editTaskDoneButton = activity.findViewById<FloatingActionButton>(R.id.fab_edit_task_done)
        editTaskDoneButton.setOnClickListener {
            presenter.saveTask(title.text.toString(), description.text.toString())
        }

        return root
    }

    override fun onResume()
    {
        super.onResume()

        presenter.start()
    }

    override fun setPresenter(presenter: IAddEditTaskContract.Presenter)
    {
        this.presenter = presenter
    }

    override fun returnToTasksList()
    {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun setTitle(title: String)
    {
        this.title.setText(title)
    }

    override fun setDescription(description: String)
    {
        this.description.setText(description)
    }

    companion object
    {
        fun getInstance() = AddEditTaskFragment()
    }
}
