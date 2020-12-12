package com.kotlindemo.model

import java.util.*

data class DataModel(
    val title: String,
    val desc: String,
    val img: Int,
    var count: Int,
    var isStart: Boolean,
    var timer: Timer
)
