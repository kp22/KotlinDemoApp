package com.kotlindemo.repo

import androidx.lifecycle.LiveData
import com.kotlindemo.model.Word
import com.kotlindemo.model.WordDao

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
}