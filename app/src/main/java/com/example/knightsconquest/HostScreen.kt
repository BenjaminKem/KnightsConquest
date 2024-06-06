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
import android.widget.Button
import com.fasterxml.jackson.module.kotlin.readValue


class HostScreen : AppCompatActivity() {
    val db = FirebaseDatabase.getInstance()
    var gameManager = GameManager()
    val gameIdGenerator = GameIdGenerator()
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_host_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backButton: Button = findViewById(R.id.backButtonHostScreen)
        backButton.setOnClickListener {
            val mainScreen = Intent(this, MainScreen::class.java)
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
                    gameManager.playerOne = PlayerState.JOINTED
                    val objectMapper = ObjectMapper()
                    val gameManagerJson = objectMapper.writeValueAsString(gameId)
                    writeGameToDatabase(gameId,objectMapper.writeValueAsString(gameManager))
                    addGameChangeListener(gameId)
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
        val databaseReference = db.reference.child("gameIds").child(gameId)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Prüfen, ob die Daten vorhanden sind
                if (snapshot.exists()) {
                    val updatedGame = snapshot.getValue(String::class.java)
                    val objectMapper = ObjectMapper()
                    println("Spiel Daten wurden aktualisiert: $updatedGame")
                    val gameManager: GameManager = objectMapper.readValue(updatedGame.toString())
                    if(gameManager.playerTwo == PlayerState.JOINTED){
                        handleUpdatedGame(gameId)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                println("Fehler beim Abhören von Spiel-Änderungen: ${error.message}")
            }
        })
    }
    private fun handleUpdatedGame(gameId: String) {
        val multiplayerPlayScreenScreen = Intent(this, MultiplayerScreen::class.java)
        multiplayerPlayScreenScreen.putExtra("gameId",gameId)
        multiplayerPlayScreenScreen.putExtra("player","blue")
        startActivity(multiplayerPlayScreenScreen)
    }
}
