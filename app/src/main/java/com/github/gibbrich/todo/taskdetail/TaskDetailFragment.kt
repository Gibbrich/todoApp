package com.github.gibbrich.todo.taskdetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.gibbrich.todo.R


/**
 * A simple [Fragment] subclass.
 */
class TaskDetailFragment : Fragment(), ITaskDetailContract.View
{
    private lateinit var presenter: ITaskDetailContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun setPresenter(presenter: ITaskDetailContract.Presenter)
    {
        this.presenter = presenter
    }
}
