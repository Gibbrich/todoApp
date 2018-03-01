package com.github.gibbrich.todo.taskdetail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.gibbrich.todo.R

class TaskDetailActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
    }

    companion object
    {
        const val EXTRA_TASK_GUID = "EXTRA_TASK_GUID"

        fun getIntent(context: Context, taskGUID: String): Intent
        {
            return Intent(context, TaskDetailActivity::class.java).apply {
                putExtra(EXTRA_TASK_GUID, taskGUID)
            }
        }
    }
}
