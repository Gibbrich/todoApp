package com.github.gibbrich.todo.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Dvurechenskiyi on 27.02.2018.
 */
object AppExecutors
{
    const val NETWORK_THREAD_COUNT = 3

    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
    private val networkThreadPool = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT)
    private val diskExecutor = Executors.newSingleThreadExecutor()

    fun executeOnMainThread(command: Runnable)
    {
        mainThreadHandler.post(command)
    }

    fun executeOnDiskThread(command: Runnable)
    {
        diskExecutor.execute(command)
    }

    fun executeOnNetworkThread(command: Runnable)
    {
        networkThreadPool.execute(command)
    }
}