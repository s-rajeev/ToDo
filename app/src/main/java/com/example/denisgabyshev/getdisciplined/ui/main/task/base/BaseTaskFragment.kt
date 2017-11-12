package com.example.denisgabyshev.getdisciplined.ui.main.task.base

import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.example.denisgabyshev.getdisciplined.R
import com.example.denisgabyshev.getdisciplined.data.db.model.Task
import com.example.denisgabyshev.getdisciplined.ui.base.BaseFragment
import com.example.denisgabyshev.getdisciplined.ui.main.MainActivity
import com.example.denisgabyshev.getdisciplined.ui.main.task.add.AddFragment
import kotlinx.android.synthetic.main.fragment_tasks_today.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import javax.inject.Inject

/**
 * Created by denisgabyshev on 11/10/2017.
 */
abstract class BaseTaskFragment: BaseFragment(), BaseTaskMvpView {
    var adapter: TaskAdapter? = null

    var frameAddTask: FrameLayout? = null

    val layoutManager = LinearLayoutManager(context)

    private val TAG = "BaseTaskFragment"

    override fun showAddTaskView() {

        frameAddTask = FrameLayout(context)
        frameAddTask?.id = 1227
        val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

        parentTasks.addView(frameAddTask, params)

        val fragment = AddFragment()

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(frameAddTask!!.id, fragment).commit()

        val paramsRV: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        paramsRV.addRule(RelativeLayout.ABOVE, frameAddTask!!.id)

        coordinatorLayout.layoutParams = paramsRV

        taskList.setPadding(0, toolbar.height, 0, 0)

        layoutManager.stackFromEnd = true

        appBar.setExpanded(false)
    }

    override fun hideAddTaskView() {
        val paramsRV: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        coordinatorLayout.layoutParams = paramsRV

        if(frameAddTask != null) {
            parentTasks.removeView(frameAddTask)
            frameAddTask = null
            fab.show()
        }

        taskList.setPadding(0, 0, 0, 0)

        layoutManager.stackFromEnd = false

        taskList.smoothScrollToPosition(adapter!!.itemCount)
    }

    override fun updateTasksList(array: ArrayList<Task>) {
        adapter?.setArray(array)
    }

    override fun setFragment() {
        setHasOptionsMenu(true)

        toolbar.setNavigationIcon(R.drawable.menu)
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        taskList.layoutManager = layoutManager
        adapter = TaskAdapter(appBar, taskList, context.applicationContext)
        taskList.adapter = adapter

        val itemDecoration = DividerItemDecoration(taskList.context, layoutManager.orientation)
        itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider))
        taskList.addItemDecoration(itemDecoration)

        fab.setOnClickListener {
            showAddTaskView()
            fab.hide()
        }

        KeyboardVisibilityEvent.setEventListener(activity, {
            if(!it && frameAddTask != null) hideAddTaskView()
        })
    }

    abstract fun updateTasksArray()

    override fun updateTasksVisibilityIcon(visibility: Boolean) {
        if(visibility) setIconCheck(R.drawable.check_show) else setIconCheck(R.drawable.check_hide)
    }

    private fun setIconCheck(drawable: Int) {
        toolbar.menu.getItem(0).icon = resources.getDrawable(drawable,null)
    }
}