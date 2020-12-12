package com.kotlindemo.main

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_play_music.*

class PlayMusicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)
        initObject()
    }

    private fun initObject() {
        btnA.setOnClickListener { playASound(R.raw.a, R.raw.a_for_apple) }
        btnB.setOnClickListener { playASound(R.raw.b, R.raw.b_for_ball) }
        btnC.setOnClickListener { playASound(R.raw.c, R.raw.c_for_cat) }
        btnD.setOnClickListener { playASound(R.raw.d, R.raw.d_for_dog) }
    }

    var isPlaying = false
    var mediaPlayer: MediaPlayer? = null
    var mediaPlayer2: MediaPlayer? = null
    private fun playASound(id1: Int, id2: Int) {
        if (isPlaying) {
            return
        }
        mediaPlayer = MediaPlayer.create(this, id1)
        mediaPlayer?.setOnPreparedListener {
            isPlaying = true
            it.start()
        }
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer2 = MediaPlayer.create(this, id2)
            mediaPlayer2?.setOnPreparedListener {
                it.start()
            }
            mediaPlayer2?.setOnCompletionListener {
                isPlaying = false
            }
        }
    }

    override fun onPause() {
        super.onPause()

        if (mediaPlayer != null && mediaPlayer?.isPlaying!!)
            mediaPlayer?.stop()
        if (mediaPlayer != null)
            mediaPlayer?.release()

        if (mediaPlayer2 != null && mediaPlayer2?.isPlaying!!)
            mediaPlayer2?.stop()
        if (mediaPlayer2 != null)
            mediaPlayer2?.release()
    }
}