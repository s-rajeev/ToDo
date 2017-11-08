package com.example.denisgabyshev.getdisciplined.data

import com.example.denisgabyshev.getdisciplined.data.db.model.Date
import com.example.denisgabyshev.getdisciplined.data.db.model.ListId
import com.example.denisgabyshev.getdisciplined.data.db.model.Task
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * Created by denisgabyshev on 11/09/2017.
 */
interface DataManager{
    fun addListId()

    fun getLastId() : Single<ListId>

    fun getAllListId(): Flowable<List<ListId>>

    fun updateListId(listId: ListId)

    fun deleteListId(listId: ListId)

    fun getListIdName(id: Long): Single<String>

    fun addDate(date: Long)

    fun addTask(dateId: Long?, listId: Long?, task: String)

    fun getDateId(date: Long): Flowable<List<Date>>

    fun getTasksByDayId(date: Long): Single<List<Task>>

    fun getTasksByListId(id: Long): Single<List<Task>>

    fun getTasksToDo(): Single<List<Task>>

    fun getAllTasks(): Flowable<List<Task>>

    fun updateTaskOrder(task: Task, order: Int)

    fun updateTaskStatus(task: Task)

    fun deleteTask(task: Task)
}