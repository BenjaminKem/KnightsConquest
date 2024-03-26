package com.example.knightsconquest

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainScreen : AppCompatActivity() {
    private var isMusicEnabled = true
    private var mediaPlayer: MediaPlayer? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)

        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            isMusicEnabled = isChecked
            if (isMusicEnabled) {
                startBackgroundMusic()
            } else {
                stopBackgroundMusic()
            }
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.titlemusic)
        startBackgroundMusic()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val joinButton: Button = findViewById(R.id.joinButton)
        joinButton.setOnClickListener {
            val joinScreen = Intent(this, JoinScreen::class.java)
            mediaPlayer?.release()
            mediaPlayer = null
            startActivity(joinScreen)
        }
        val hostButton: Button = findViewById(R.id.hostButton)
        hostButton.setOnClickListener {
            val hostScreen = Intent(this, HostScreen::class.java)
            mediaPlayer?.release()
            mediaPlayer = null
            startActivity(hostScreen)
        }
        val howToPlayButton: Button = findViewById(R.id.howToPlayButton)
        howToPlayButton.setOnClickListener {
            val howToPlayScreen = Intent(this, HowToPlay::class.java)
            mediaPlayer?.release()
            mediaPlayer = null
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(howToPlayScreen)
            }, 1000)
        }
        val PlayLocalButton: Button = findViewById(R.id.PlayLocalButton)
        PlayLocalButton.setOnClickListener {
            val localPlayScreenScreen = Intent(this, LocalPlayScreen::class.java)
            mediaPlayer?.release()
            mediaPlayer = null
            startActivity(localPlayScreenScreen)
        }
    }
    private fun startBackgroundMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.titlemusic)
            mediaPlayer?.isLooping = true
        }
        mediaPlayer?.start()
    }

    private fun stopBackgroundMusic() {
        mediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
