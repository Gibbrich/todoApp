package com.github.gibbrich.todo.addedittask

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.source.TasksLocalDataSource

class AddEditTaskActivity : AppCompatActivity()
{
    private lateinit var presenter: IAddEditTaskContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        setResult(Activity.RESULT_CANCELED)

        var fragment = fragmentManager.findFragmentById(R.id.contentFrame) as AddEditTaskFragment?
        if (fragment == null)
        {
            fragment = AddEditTaskFragment.getInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit()
        }

        presenter = AddEditTaskPresenter(fragment, TasksLocalDataSource)
    }

    companion object
    {
        const val REQUEST_ADD_EDIT_TASK = 1
    }
}
