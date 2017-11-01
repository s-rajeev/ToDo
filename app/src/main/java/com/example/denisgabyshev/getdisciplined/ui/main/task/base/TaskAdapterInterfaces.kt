package com.example.denisgabyshev.getdisciplined.ui.main.task.base

import android.support.v7.widget.RecyclerView

/**
 * Created by denisgabyshev on 28/10/2017.
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(oldPos: Int, newPos: Int): Boolean
    fun onItemSwipe(pos: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}