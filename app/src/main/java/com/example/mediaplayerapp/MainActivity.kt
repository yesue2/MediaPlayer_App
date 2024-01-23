package com.example.mediaplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.sample)
        mPlayer?.start()
    }
}