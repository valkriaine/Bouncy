package com.factor.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.factor.bouncy.BouncyRecyclerView

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<BouncyRecyclerView>(R.id.rc).adapter = Adapter()
        findViewById<BouncyRecyclerView>(R.id.rc).layoutManager = GridLayoutManager(this, 2)
    }
}