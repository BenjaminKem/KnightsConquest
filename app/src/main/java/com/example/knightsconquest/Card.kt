package com.example.knightsconquest

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
class Card(
    val name: String = "",
    val movementCount: Int = 0,
    val movements: Array<Array<Int>> = emptyArray(),
    private val imageUrl: String = ""
) {
    override fun toString(): String {
        return "Name: $name \nBewegungsanzahl: $movementCount \nBewegungen: ${movements.contentDeepToString()} \nBild-URL: $imageUrl"
    }
}
