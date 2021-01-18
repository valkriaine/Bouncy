package com.factor.bouncy.util

import androidx.recyclerview.widget.RecyclerView.ViewHolder

interface DragDropAdapter<T: ViewHolder>
{
    fun onItemMoved(fromPosition: Int, toPosition: Int)
    fun onItemSwipedToStart(viewHolder: ViewHolder, positionOfItem: Int)
    fun onItemSwipedToEnd(viewHolder: ViewHolder, positionOfItem: Int)
    fun onItemSelected(viewHolder: ViewHolder?)
    fun onItemReleased(viewHolder: ViewHolder)
}