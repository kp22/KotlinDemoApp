package com.kotlindemo.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kotlindemo.adapter.PagerAdapter
import com.kotlindemo.fragment.TwoFragment
import com.kotlindemo.model.Word
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity() {


    lateinit var adapter: PagerAdapter

    companion object{
        val newWordActivityRequestCode = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        initTab()
    }

    private fun initTab() {

        adapter = PagerAdapter(supportFragmentManager)
        viewPager!!.adapter = adapter

        adapter.getItem(1)

        tabs!!.setupWithViewPager(viewPager)
        tabs!!.getTabAt(0)!!.setText(R.string.tab1)
        tabs!!.getTabAt(1)!!.setText(R.string.tab2)


        tabs!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
            }
        })
    }
}
