package com.example.denisgabyshev.getdisciplined.ui.main.task.todo

import android.os.Bundle
import android.view.*
import com.example.denisgabyshev.getdisciplined.R
import com.example.denisgabyshev.getdisciplined.ui.main.task.base.BaseTaskFragment
import kotlinx.android.synthetic.main.fragment_tasks_todo.*
import javax.inject.Inject

/**
 * Created by denisgabyshev on 11/10/2017.
 */
class ToDoListFragment : BaseTaskFragment(), ToDoListMvpView {
    private val TAG = "ToDListFragment"

    @Inject lateinit var presenter: ToDoListMvpPresenter<ToDoListMvpView>

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.activityComponent.inject(this)
        presenter.onAttach(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_tasks_todo, container, false)

    override fun setToolbar(title: Long) {
        toolbar.title = resources.getString(R.string.todo)
    }

    override fun setFragment() {
        super.setFragment()

        setMenu()

        presenter.isTodayExist()
        presenter.getTasksVisibility()
    }

    private fun setMenu() {
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.check -> {
                    presenter.changeTaskVisibility()
                }
            }
            false
        }
    }

    override fun updateTasksArray() {
        presenter.isTodayExist()
    }


}