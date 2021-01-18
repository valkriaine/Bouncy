package com.factor.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.factor.bouncy.BouncyRecyclerView
import java.util.*

//example adapter
class MyAdapter(size : Int) : BouncyRecyclerView.Adapter<MyAdapter.MyViewHolder>()
{

    //example data set
    private val data = ArrayList<String>()

    //initialize data set
    init
    {
        for (i in 1..size) data.add("data: $i")
    }


    /*
    ** RecyclerView Adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int
    {
        return data.size
    }


    /*
    ** DragDrop interface
     */

    //reorder data set on item moved
    override fun onItemMoved(fromPosition: Int, toPosition: Int)
    {
        Collections.swap(data, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    //update data
    override fun onItemSwipedToStart(viewHolder: ViewHolder, positionOfItem: Int)
    {
        data[positionOfItem] += "\nswiped to left"
        notifyItemChanged(positionOfItem)
    }

    //update data
    override fun onItemSwipedToEnd(viewHolder: ViewHolder, positionOfItem: Int)
    {
        data[positionOfItem] += "\nswiped to right"
        notifyItemChanged(positionOfItem)
    }

    //item long pressed
    override fun onItemSelected(viewHolder: ViewHolder?)
    {
        viewHolder?.itemView?.alpha = 0.5f
    }

    //item released (after long press)
    override fun onItemReleased(viewHolder: ViewHolder)
    {
        viewHolder.itemView.alpha = 1f
    }



    //ViewHolder
    class MyViewHolder(itemView: View) : ViewHolder(itemView)
    {
        fun bindData(data : String)
        {
            itemView.findViewById<TextView>(R.id.data_text).text = data
        }
    }
}
