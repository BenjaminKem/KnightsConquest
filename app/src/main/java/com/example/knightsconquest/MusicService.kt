package com.example.knightsconquest

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentSongResId: Int = -1

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra("action")
        val songResId = intent?.getIntExtra("songResId", -1) ?: -1

        when (action) {
            "pause" -> pauseMusic()
            "resume" -> resumeMusic()
            else -> {
                if (songResId != -1 && (mediaPlayer == null || currentSongResId != songResId)) {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer.create(this, songResId)
                    mediaPlayer?.isLooping = true
                    currentSongResId = songResId
                    mediaPlayer?.start()
                } else if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                    mediaPlayer?.start()
                }
            }
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

    private fun pauseMusic() {
        mediaPlayer?.pause()
    }

    private fun resumeMusic() {
        mediaPlayer?.start()
    }
}
