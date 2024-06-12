package com.example.knightsconquest

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentSongResId: Int = R.raw.titlemusic

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songResId = intent?.getIntExtra("songResId", R.raw.titlemusic) ?: R.raw.titlemusic

        if (mediaPlayer == null || currentSongResId != songResId) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, songResId)
            mediaPlayer?.isLooping = true
            currentSongResId = songResId
            mediaPlayer?.start()
        } else if (!mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
