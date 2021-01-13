package com.factor.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.factor.bouncy.BouncyRecyclerView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Adapter extends BouncyRecyclerView.Adapter
{
    final ArrayList<String> data = new ArrayList<>();


    public Adapter()
    {
        for (int i = 0; i < 20; i ++)
            data.add("data: " + i);
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemSwipedToStart(@Nullable RecyclerView.ViewHolder viewHolder, int positionOfItem) {

    }

    @Override
    public void onItemSwipedToEnd(@Nullable RecyclerView.ViewHolder viewHolder, int positionOfItem) {

    }

    @Override
    public void onItemSelected(@Nullable RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onItemReleased(@Nullable RecyclerView.ViewHolder viewHolder) {

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public MyViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
        }
    }
}
