package com.example.knightsconquest

class Card(private val name: String, val movementCount: Int, val movements: Array<Array<Int>>, private val imageUrl: String) {
    init {
        // println("Eine neue Karte mit dem Namen $name und den Bewegungen ${movements.contentDeepToString()} wurde erstellt.")
    }
    override fun toString(): String {
        return "Name: $name \nBewegungsanzahl: $movementCount \nBewegungen: $movements.contentDeepToString()} \nBild-URL: $imageUrl"
    }
}

