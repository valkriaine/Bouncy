package com.factor.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.factor.bouncy.BouncyRecyclerView


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<BouncyRecyclerView>(R.id.rc).adapter = MyAdapter(30)
        findViewById<BouncyRecyclerView>(R.id.rc).layoutManager = LinearLayoutManager(this)
    }
}