package com.example.knightsconquest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.IOException

class HostScreen : AppCompatActivity() {
    var gameId : String = ""
    var game = GameController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_host_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        game.startGame()
        val gameIdGenerator = GameIdGenerator()

        // Callback-Funktion zum Verarbeiten der generierten Game-ID
        val callback: (String) -> Unit = { gameId ->
            this.gameId=gameId
        }
        // Aufruf der Methode generateUniqueGameId mit dem Callback
        gameIdGenerator.generateUniqueGameId(callback)
    }
}