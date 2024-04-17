package com.example.knightsconquest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.database.FirebaseDatabase


class HostScreen : AppCompatActivity() {
    val db = FirebaseDatabase.getInstance()
    var gameID : String = ""
    var game = GameController()
    var objectMapper = ObjectMapper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_host_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Callback-Funktion zum Verarbeiten der generierten Game-ID
        val callback: (String) -> Unit = { gameId ->
            this.gameID=gameId
            val gameAsJson = objectMapper.writeValueAsString(game)
            db.getReference(gameID).setValue(gameAsJson)
        }
        // Aufruf der Methode generateUniqueGameId mit dem Callback
        val gameIDGenerator = GameIdGenerator()
        gameIDGenerator.generateUniqueGameId(callback)
        game.startGame()

        //Test für einen Eintrag aus der Datenbank
        /*
        val reference = db.getReference("95542")
        reference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val gameAsJson : String = task.result?.value.toString()
                println(gameAsJson)
                val testgame: GameController = objectMapper.readValue(gameAsJson, GameController::class.java)
                testgame.gameBoard.printBoard()
                println(testgame.redCards[0])
                // Hier kannst du das Ergebnis weiterverarbeiten
            } else {
                // Fehlerbehandlung, falls die Aufgabe nicht erfolgreich war
                val exception = task.exception
                // Hier kannst du den Fehler behandeln oder protokollieren
        }
        */
    }
}
