package com.example.knightsconquest

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GameIdGenerator() {
    val db = FirebaseDatabase.getInstance()
    val length = 5
    fun generateGameId(): String {
        val allowedChars = ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun generateUniqueGameId(callback: (String) -> Unit) {
        var gameId = generateGameId()

        // Funktion zur Überprüfung der Eindeutigkeit mit Callback
        fun checkUnique() {
            isGameIdUnique(gameId) { isUnique ->
                if (isUnique) {
                    callback(gameId) // Game-ID ist eindeutig, Callback aufrufen
                } else {
                    gameId = generateGameId() // Game-ID neu generieren
                    checkUnique() // Erneut überprüfen
                }
            }
        }
        checkUnique() // Überprüfung auf Eindeutigkeit starten
    }
    fun isGameIdUnique(gameId: String, callback: (Boolean) -> Unit) {
        val reference = db.getReference(gameId)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                callback(!dataSnapshot.exists())
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback(false) // Bei Fehler als nicht eindeutig behandeln
            }
        })
    }
}
