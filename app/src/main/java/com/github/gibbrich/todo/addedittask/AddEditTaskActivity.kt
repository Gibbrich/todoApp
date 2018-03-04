package com.github.gibbrich.todo.addedittask

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.source.TasksRepository

class AddEditTaskActivity : AppCompatActivity()
{
    private lateinit var presenter: IAddEditTaskContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var fragment = fragmentManager.findFragmentById(R.id.contentFrame) as AddEditTaskFragment?
        if (fragment == null)
        {
            fragment = AddEditTaskFragment.getInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit()
        }

        val taskGUID = intent.getStringExtra(EXTRA_TASK_GUID)

        presenter = AddEditTaskPresenter(taskGUID, fragment, TasksRepository)
    }

    override fun onSupportNavigateUp(): Boolean
    {
        onBackPressed()
        return true
    }

    companion object
    {
        const val REQUEST_ADD_TASK = 1
        const val REQUEST_EDIT_TASK = 2
        private const val EXTRA_TASK_GUID = "EXTRA_TASK_GUID"

        fun getIntent(context: Context, taskGUID: String?): Intent
        {
            val intent = Intent(context, AddEditTaskActivity::class.java)
            if (taskGUID != null)
            {
                intent.putExtra(EXTRA_TASK_GUID, taskGUID)
            }
            return intent
        }
    }
}
