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
    private var isMusicEnabled = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_blue_win_screen)
        isMusicEnabled = intent.getBooleanExtra("isMusicEnabled", false)

        if (isMusicEnabled) {
            startService(Intent(this, MusicService::class.java))
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
}