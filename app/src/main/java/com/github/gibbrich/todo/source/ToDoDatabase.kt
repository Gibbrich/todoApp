package com.github.gibbrich.todo.source

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.github.gibbrich.todo.ToDoApplication
import com.github.gibbrich.todo.model.Task

/**
 * Created by Dvurechenskiyi on 27.02.2018.
 */

@Database(entities = arrayOf(Task::class), version = 1)
abstract class ToDoDatabase : RoomDatabase()
{
    abstract val dao: Dao

    companion object
    {
        val instance by lazy {
            Room.databaseBuilder(ToDoApplication.instance.applicationContext, ToDoDatabase::class.java, "ToDoDB.db")
                    .build()
        }
    }
}