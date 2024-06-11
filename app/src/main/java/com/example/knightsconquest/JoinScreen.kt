package com.example.knightsconquest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.Toast
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JoinScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_join_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        val gameIdInput = findViewById<EditText>(R.id.idInput)
        val joinButton = findViewById<Button>(R.id.joinButton)

        val backButton: Button = findViewById(R.id.backButtonJoinScreen)
        backButton.setOnClickListener {
            val mainScreen = Intent(this, MainScreen::class.java)
            startActivity(mainScreen)
        }

        joinButton.setOnClickListener {
            val gameId = gameIdInput.text.toString()
            if (gameId.isNotEmpty()) {
                joinGame(gameId)
            } else {
                Toast.makeText(this, "Bitte Spiel-ID eingeben", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun joinGame(gameId: String) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val databaseReference = db.reference.child("gameIds").child(gameId)
                databaseReference.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        // Spiel existiert
                        println("Spiel gefunden: $gameId")
                        getGameValue(gameId, onSuccess = { gameValue ->
                            // Erfolgreiches Abrufen der Spieldaten
                            val objectMapper = ObjectMapper()
                            val gameManager: GameManager = objectMapper.readValue(gameValue)
                            if(!(gameManager.playerTwo == PlayerState.JOINED)){
                            gameManager.playerTwo = PlayerState.JOINED
                            writeGameToDatabase(gameId,objectMapper.writeValueAsString(gameManager))
                            val multiplayerPlayScreenScreen = Intent(this, MultiplayerScreen::class.java)
                            multiplayerPlayScreenScreen.putExtra("gameId",gameId)
                            multiplayerPlayScreenScreen.putExtra("player","red")
                            startActivity(multiplayerPlayScreenScreen)
                            }else{Toast.makeText(
                                this,
                                "Spiel ist voll: $gameId",
                                Toast.LENGTH_SHORT
                            ).show()}
                        }, onFailure = { exception ->
                            // Fehler beim Abrufen der Spieldaten
                            println("Fehler: ${exception.message}")
                        })
                    }
                        else {
                        // Spiel existiert nicht
                        Toast.makeText(
                            this,
                            "Spiel nicht gefunden: $gameId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener { exception ->
                    println("Fehler beim Abrufen der Spiel-ID: ${exception.message}")
                    Toast.makeText(this, "Fehler beim Abrufen der Spiel-ID", Toast.LENGTH_SHORT).show()
                }
            } else {
                println("Anonymous authentication failed: ${task.exception?.message}")
                Toast.makeText(this, "Authentifizierung fehlgeschlagen", Toast.LENGTH_SHORT).show()
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
    fun getGameValue(gameId: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("gameIds").child(gameId)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    try {
                        val gameValue = snapshot.getValue(String::class.java)
                        if (gameValue != null) {
                            onSuccess(gameValue)
                        } else {
                            onFailure(Exception("Game value is null"))
                        }
                    } catch (e: Exception) {
                        onFailure(e)
                    }
                } else {
                    onFailure(Exception("Game not found"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(Exception(error.message))
            }
        })
    }

}

