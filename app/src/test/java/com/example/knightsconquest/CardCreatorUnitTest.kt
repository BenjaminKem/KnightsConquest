package com.example.knightsconquest


import CardCreator
import org.junit.Test
import org.junit.Assert.*

class CardCreatorUnitTest {

    //Test for 5 Cards in Card list
    @Test
    fun `test createCardList contains expected number of cards`(){
        val cardCreator = CardCreator()
        val cardList = cardCreator.getFiveRandomCards()
        assertEquals("Expected 5 cards in the list",5, cardList.size)
    }

    //Test for only unique cards in Card list
    @Test
    fun `test getFiveRandomCards returns unique cards only`(){
        val cardCreator = CardCreator()
        val cardList = cardCreator.getFiveRandomCards()
        val cardNames = cardList.map {it.name}
        val uniqueCards = cardNames.toSet()
        assertEquals("Expected only unique cards in cardList",cardNames.size,uniqueCards.size)
    }

    //Test to check if getFiveRandomCards() does not return an out of bounds value
    @Test
    fun `test getFiveRandomCards does not return out of bounds`(){
        val cardCreator = CardCreator()
        val cardList = cardCreator.getFiveRandomCards()
        assertTrue("Every card in list should have a name",cardList.all { it.name.isNotEmpty() })
        assertTrue("All Cards should have a movement count greater than 0", cardList.all { it.movementCount > 0 })
    }
}