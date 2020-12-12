package com.kotlindemo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kotlindemo.adapter.ListAdapter
import com.kotlindemo.model.DataModel
import kotlinx.android.synthetic.main.activity_recycle_view.*
import java.util.*

class RecycleActivity : AppCompatActivity() {

    lateinit var adapter: ListAdapter;
    val TAG = RecycleActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)
        val list: List<DataModel> = getMyData()
        adapter = ListAdapter(list)
        rvMain.adapter = adapter
    }

    private fun getMyData(): List<DataModel> {
        val list = mutableListOf<DataModel>()
        var count = 0;
        for (index in 1..20) {
            val modle: DataModel
            when (count) {
                0 -> {
                    count++
                    modle = DataModel("Title-" + index, "Count - 0", R.drawable.placeholder1, 0, false, Timer())
                    list.add(modle)
                }
                1 -> {
                    count++
                    modle = DataModel("Title-" + index, "Count - 0", R.drawable.placeholder2, 0, false, Timer())
                    list.add(modle)
                }
                2 -> {
                    count = 0
                    modle = DataModel("Title-" + index, "Count - 0", R.drawable.placeholder3, 0, false, Timer())
                    list.add(modle)
                }
            }
        }
        return list
    }
}