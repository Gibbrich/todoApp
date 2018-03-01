package com.github.gibbrich.todo.tasks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.gibbrich.todo.R

class TasksActivity : AppCompatActivity()
{
    private lateinit var presenter: ITasksContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tasksFragment = fragmentManager.findFragmentById(R.id.contentFrame) as TasksFragment?
        if (tasksFragment == null)
        {
            tasksFragment = TasksFragment.createInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentFrame, tasksFragment)
                    .commit()
        }

        presenter = TasksPresenter(tasksFragment)
    }
}
