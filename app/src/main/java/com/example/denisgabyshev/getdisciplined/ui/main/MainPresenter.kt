package com.example.denisgabyshev.getdisciplined.ui.main

import android.util.Log
import com.example.denisgabyshev.getdisciplined.data.DataManager
import com.example.denisgabyshev.getdisciplined.data.db.model.ListId
import com.example.denisgabyshev.getdisciplined.ui.base.BasePresenter
import com.example.denisgabyshev.getdisciplined.ui.main.task.today.TaskFragment
import com.example.denisgabyshev.getdisciplined.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by denisgabyshev on 18/09/2017.
 */
class MainPresenter<V : MainMvpView> @Inject
constructor(dataManager: DataManager,
            schedulerProvider: SchedulerProvider?,
            compositeDisposable: CompositeDisposable?) :
        BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), MainMvpPresenter<V>{

    private val TAG = "MainPresenter"

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        mvpView.setTaskFragment(TaskFragment())

        dataManager.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    Log.d(TAG, "todo items")
                    for(item in it) {
                        Log.d(TAG, "$item")
                    }
                }, Throwable::printStackTrace)
    }

    override fun onDrawerClick() {
        mvpView?.openDrawer()
    }

    override fun onAddListId() {
        Single.fromCallable {
            dataManager.addListId()
        }.subscribeOn(Schedulers.io())
            .subscribe({
                showDialog()
            }, Throwable::printStackTrace)
    }

    override fun navigationListIds() {
        dataManager.getAllListId()
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mvpView?.updateNavigationArray(it as ArrayList<ListId>)
                }
    }

    private fun showDialog() {
        dataManager.getLastId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mvpView?.showListIdNameDialog(it)
                }, Throwable::printStackTrace)
    }


}