package com.kotlindemo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CustomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)
        init()
    }

    private fun init() {

    }
}