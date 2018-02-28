package com.github.gibbrich.todo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.gibbrich.todo.R
import com.github.gibbrich.todo.ToDoApplication
import com.github.gibbrich.todo.fragments.TasksFragment

class MainActivity : AppCompatActivity()
{
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
    }
}
