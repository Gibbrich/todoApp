package com.github.gibbrich.todo.utils

import android.util.Log
import java.util.*

/**
 * Created by Артур on 06.03.2018.
 */

fun logStart(tag: String)
{
    Log.d(tag, "Operation started on ${Thread.currentThread().name} at ${Calendar.getInstance().time}")
}

fun logEnd(tag: String)
{
    Log.d(tag, "Operation finished on ${Thread.currentThread().name} at ${Calendar.getInstance().time}")
}