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
import com.google.firebase.database.FirebaseDatabase

class BlueWinScreen : AppCompatActivity() {
    private var isMusicEnabled = true
    private var mediaPlayer: MediaPlayer? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_blue_win_screen)

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

        val mainMenuButton: Button = findViewById(R.id.mainMenuButton)
        mainMenuButton.setOnClickListener {
            val mainScreenIntent = Intent(this, MainScreen::class.java)
            mediaPlayer?.release()
            mediaPlayer = null
            startActivity(mainScreenIntent)
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
