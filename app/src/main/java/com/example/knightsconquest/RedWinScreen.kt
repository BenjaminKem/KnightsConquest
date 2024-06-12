package com.example.knightsconquest

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class RedWinScreen : AppCompatActivity() {
    private var isMusicEnabled = false
    private var isMusicBound = false
    private var musicPaused = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_red_win_screen)
        isMusicEnabled = intent.getBooleanExtra("isMusicEnabled", false)

        if (isMusicEnabled) {
            startService(Intent(this, MusicService::class.java))
        }


        val mainMenuButton: Button = findViewById(R.id.mainMenuButton)
        mainMenuButton.setOnClickListener {
            val mainScreenIntent = Intent(this, MainScreen::class.java)
            mainScreenIntent.putExtra("isMusicEnabled", isMusicEnabled)
            startActivity(mainScreenIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isMusicEnabled) {
            stopService(Intent(this, MusicService::class.java))
        }
    }
    override fun onStop() {
        super.onStop()
        Log.d("Mainscreen", "onStop called")
        if (isMusicEnabled) {
            pauseMusic()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Mainscreen", "onResume called")
        if (musicPaused && isMusicEnabled) {
            resumeMusic()
        }
    }
    private fun pauseMusic() {
        val musicServiceIntent = Intent(this, MusicService::class.java)
        stopService(musicServiceIntent)
        musicPaused = true
    }

    private fun resumeMusic() {
        val musicIntent = Intent(this, MusicService::class.java).apply {
            putExtra("songResId", R.raw.battlemusic)
            putExtra("resume", true)
        }
        startService(musicIntent)
        musicPaused = false
    }
}
