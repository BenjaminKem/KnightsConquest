package com.example.knightsconquest

import GameIdGenerator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.firebase.database.DatabaseReference


class HostScreen : AppCompatActivity() {
    val db = FirebaseDatabase.getInstance()
    var gameManager = GameManager()
    val gameIdGenerator = GameIdGenerator()
    val auth = FirebaseAuth.getInstance()
    private var valueEventListener: ValueEventListener? = null
    private lateinit var databaseReference: DatabaseReference
    private var isMusicEnabled = false
    private var isMusicBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_host_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        isMusicEnabled = intent.getBooleanExtra("isMusicEnabled", false)
        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)
        musicSwitch.isChecked = isMusicEnabled
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            isMusicEnabled = isChecked
            if (isMusicEnabled) {
                startService(Intent(this, MusicService::class.java))
            } else {
                stopService(Intent(this, MusicService::class.java))
            }
        }
        if (isMusicEnabled) {
            startService(Intent(this, MusicService::class.java))
        }

        val backButton: Button = findViewById(R.id.backButtonHostScreen)
        backButton.setOnClickListener {
            val mainScreen = Intent(this, MainScreen::class.java)
            mainScreen.putExtra("isMusicEnabled", isMusicEnabled)
            startActivity(mainScreen)
        }
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                gameIdGenerator.generateUniqueGameId { gameId ->
                    // Verarbeitung der generierten Spiel-ID
                    println("Generierte eindeutige Spiel-ID: $gameId")
                    gameManager.gameID = gameId;
                    writeGameIdToDatabase(gameId)
                    gameManager.game.startGame()
                    gameManager.playerOne = PlayerState.JOINED
                    val objectMapper = ObjectMapper()
                    writeGameToDatabase(gameId,objectMapper.writeValueAsString(gameManager))
                    addGameChangeListener(gameId)
                    val hostButtonTextView: TextView = findViewById(R.id.HostButton)
                    hostButtonTextView.text = "Your GameID: $gameId"
                }
            } else {
                println("Anonymous authentication failed: ${task.exception?.message}")
            }
        }
    }
    private fun writeGameIdToDatabase(gameId: String) {
        var databaseReference = db.reference.child("gameIds").child(gameId)
        databaseReference.setValue(gameId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Spiel-ID erfolgreich in die Datenbank geschrieben.")
            } else {
                println("Fehler beim Schreiben der Spiel-ID in die Datenbank: ${task.exception?.message}")
            }
        }
    }
    private fun writeGameToDatabase(gameId: String,game: String) {
        var databaseReference = db.reference.child("gameIds").child(gameId)
        databaseReference.setValue(game).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Spiel Daten erfolgreich in die Datenbank geschrieben.")
            } else {
                println("Fehler beim Schreiben der Spiel-ID in die Datenbank: ${task.exception?.message}")
            }
        }
    }
    private fun addGameChangeListener(gameId: String) {
        databaseReference = db.reference.child("gameIds").child(gameId)
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("MultiplayerScreen", "HostScreen Called")
                // Prüfen, ob die Daten vorhanden sind
                if (snapshot.exists()) {
                    val updatedGame = snapshot.getValue(String::class.java)
                    val objectMapper = ObjectMapper()
                    println("Spiel Daten wurden aktualisiert: $updatedGame")
                    gameManager = objectMapper.readValue(updatedGame.toString())
                    if (gameManager.playerTwo == PlayerState.JOINED) {
                        handleUpdatedGame(gameId)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Fehler beim Abhören von Spiel-Änderungen: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(valueEventListener as ValueEventListener)
    }
    private fun handleUpdatedGame(gameId: String) {
        val multiplayerPlayScreenScreen = Intent(this, MultiplayerScreen::class.java)
        multiplayerPlayScreenScreen.putExtra("gameId",gameId)
        multiplayerPlayScreenScreen.putExtra("player","blue")
        multiplayerPlayScreenScreen.putExtra("isMusicEnabled", isMusicEnabled)
        multiplayerPlayScreenScreen.putExtra("songResId", R.raw.battlemusic)
        startActivity(multiplayerPlayScreenScreen)
    }
    override fun onStop() {
        super.onStop()
        valueEventListener?.let {
            databaseReference.removeEventListener(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isMusicEnabled) {
            stopService(Intent(this, MusicService::class.java))
        }
    }
}
