package com.github.gibbrich.todo.taskdetail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.source.TasksLocalDataSource

class TaskDetailActivity : AppCompatActivity()
{
    private lateinit var presenter: TaskDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        var fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as TaskDetailFragment?
        if (fragment == null)
        {
            fragment = TaskDetailFragment.getInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit()
        }

        val taskGUID = intent.extras[EXTRA_TASK_GUID] as String

        presenter = TaskDetailPresenter(taskGUID, fragment, TasksLocalDataSource)
    }

    companion object
    {
        private const val EXTRA_TASK_GUID = "EXTRA_TASK_GUID"

        fun getIntent(context: Context, taskGUID: String): Intent
        {
            return Intent(context, TaskDetailActivity::class.java).apply {
                putExtra(EXTRA_TASK_GUID, taskGUID)
            }
        }
    }
}
