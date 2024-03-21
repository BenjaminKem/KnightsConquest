package com.example.knightsconquest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class CardCreator {
    fun createCardList(): List<Card> {
        val objectMapper = jacksonObjectMapper()
        val file = File("app/src/main/res/raw/cards.json")
        val cardList = mutableListOf<Card>()

        if (file.exists()) {
            val jsonData = file.readText()
            val cards: List<Card> = objectMapper.readValue(jsonData)

            cardList.addAll(cards)

            cardList.forEachIndexed { index, card ->
                println("Karte ${index + 1}:")
                println("Name: ${card.name}")
                println("Bewegungsanzahl: ${card.movementCount}")
                println("Bewegungen: ${card.movements.contentDeepToString()}")
                println("Bild-URL: ${card.imageUrl}")
                println("")
            }
        } else {
            println("Die Datei cards.json existiert nicht.")
        }

        return cardList
    }
}
