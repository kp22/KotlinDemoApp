package com.kotlindemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
class Word{
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "word")
    var word: String? = null
}
