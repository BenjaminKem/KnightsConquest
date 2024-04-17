package com.example.knightsconquest

import CardCreator
import android.content.Context
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class GameController() {
    val gameBoard = GameBoard()
    val cardCreator = CardCreator()
    val redCards = mutableListOf<Card>()
    val blueCards = mutableListOf<Card>()
    val stack = mutableListOf<Card>()
    fun startGame() {
        // Initialisiere das Spielbrett und generiere die benötigten Karten
        gameBoard.init()
        val fiveRandomCards = cardCreator.getFiveRandomCards()

        // Aufteilen der Kartenliste in Farben
        redCards.addAll(fiveRandomCards.take(2))
        blueCards.addAll(fiveRandomCards.slice(2 until 4))
        stack.addAll(fiveRandomCards.takeLast(1))
        // Weitere Spiellogik hier einfügen
    }
    fun makeMove(card: Card, fromCords: Array<Int>, toCords: Array<Int>) : Boolean{
        if(gameBoard.makeMove(card,fromCords[0],fromCords[1],toCords[0],toCords[1])){
            val tempCard = card
            if(gameBoard.turnIndicator == TileColor.RED){
                val index = redCards.indexOf(card)
                redCards[index] = stack[0]
            }else{
                val index = blueCards.indexOf(card)
                blueCards[index] = stack[0]
            }
            stack[0] = tempCard
            gameBoard.endTurn()
            return true
        }
        return false
    }

    fun getBlueCard(index: Int): Card {
        return blueCards.getOrNull(index) ?: throw IndexOutOfBoundsException("Index $index out of bounds for blueCards")
    }
    fun getRedCard(index: Int): Card {
        return redCards.getOrNull(index) ?: throw IndexOutOfBoundsException("Index $index out of bounds for blueCards")
    }
    fun getStackCard(): Card {
        return stack[0]
    }
}
