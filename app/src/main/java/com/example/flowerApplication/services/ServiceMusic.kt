package com.example.flowerApplication.services

import android.app.Application
import android.media.MediaPlayer
import com.example.flowerApplication.R

class ServiceMusic(private val app: Application) {

    private lateinit var mAudioHelper: MediaPlayer

    init {
        runServiceMusic()
        stopServiceMusic()
    }

    fun runServiceMusic() {
        mAudioHelper = MediaPlayer.create(app, R.raw.music2)
        mAudioHelper.start()
        mAudioHelper.isLooping = true
    }

    fun stopServiceMusic() {
        mAudioHelper.stop()
        mAudioHelper.release()
    }
}
