package com.github.gibbrich.todo

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by Dvurechenskiyi on 27.02.2018.
 */
class ToDoApplication: Application()
{
    override fun onCreate()
    {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        instance = this
    }

    companion object
    {
        lateinit var instance: ToDoApplication
            private set
    }
}