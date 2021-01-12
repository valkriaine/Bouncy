package com.factor.bouncy.util

import androidx.recyclerview.widget.RecyclerView

interface DragDropAdapter
{
    fun onItemMoved(fromPosition: Int, toPosition: Int)
    fun onItemSwipedToStart(viewHolder: RecyclerView.ViewHolder?, positionOfItem: Int)
    fun onItemSwipedToEnd(viewHolder: RecyclerView.ViewHolder?, positionOfItem: Int)
    fun onItemSelected(viewHolder: RecyclerView.ViewHolder?)
    fun onItemReleased(viewHolder: RecyclerView.ViewHolder?)
}