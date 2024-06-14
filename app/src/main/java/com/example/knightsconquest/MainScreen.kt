package com.example.knightsconquest

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainScreen : AppCompatActivity() {
    private var isMusicEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)

        isMusicEnabled = intent.getBooleanExtra("isMusicEnabled", true)
        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)
        musicSwitch.isChecked = isMusicEnabled

        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            isMusicEnabled = isChecked
            val songResId = R.raw.titlemusic
            val musicIntent = Intent(this, MusicService::class.java).apply {
                putExtra("songResId", songResId)
            }
            if (isMusicEnabled) {
                startService(musicIntent)
            } else {
                stopService(musicIntent)
            }
        }

        if (isMusicEnabled) {
            val musicIntent = Intent(this, MusicService::class.java).apply {
                putExtra("songResId", R.raw.titlemusic)
            }
            startService(musicIntent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val joinButton: Button = findViewById(R.id.joinButton)
        joinButton.setOnClickListener {
            val joinScreen = Intent(this, JoinScreen::class.java)
            joinScreen.putExtra("isMusicEnabled", isMusicEnabled)
            startActivity(joinScreen)
        }
        val hostButton: Button = findViewById(R.id.hostButton)
        hostButton.setOnClickListener {
            val hostScreen = Intent(this, HostScreen::class.java)
            hostScreen.putExtra("isMusicEnabled", isMusicEnabled)
            startActivity(hostScreen)
        }
        val howToPlayButton: Button = findViewById(R.id.howToPlayButton)
        howToPlayButton.setOnClickListener {
            val howToPlayScreen = Intent(this, HowToPlay::class.java)
            howToPlayScreen.putExtra("isMusicEnabled", isMusicEnabled)
            startActivity(howToPlayScreen)
        }
        val playLocalButton: Button = findViewById(R.id.PlayLocalButton)
        playLocalButton.setOnClickListener {
            val localPlayScreenScreen = Intent(this, LocalPlayScreen::class.java)
            localPlayScreenScreen.putExtra("isMusicEnabled", isMusicEnabled)
            localPlayScreenScreen.putExtra("songResId", R.raw.battlemusic)
            startActivity(localPlayScreenScreen)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isMusicEnabled) {
            stopService(Intent(this, MusicService::class.java))
        }
    }
}
