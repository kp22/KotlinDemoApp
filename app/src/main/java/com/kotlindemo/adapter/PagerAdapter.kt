package com.kotlindemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kotlindemo.fragment.TabOneFragment
import com.kotlindemo.fragment.TwoFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }
//    init {
//        var twoFragment = TwoFragment()
//        var oneFragment = TabOneFragment()
//    }
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TabOneFragment()
            }
            1 -> {
                return TwoFragment()
            }
        }
        return TabOneFragment()
    }


}