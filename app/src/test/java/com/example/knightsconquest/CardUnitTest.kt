package com.example.knightsconquest


import org.junit.Test

import org.junit.Assert.*

class CardUnitTest {

    //Tests the default Constructor
    @Test
    fun `test default constructor`(){
        val card = Card()
        assertEquals("Default Name should be an empty String","",card.name)
        assertEquals("Default movement count should be 0",0, card.movementCount)
        assertArrayEquals("Default Movements should be an empty array", emptyArray(),card.movements)
    }

    //Tests the constructor with parameters
    @Test
    fun `test parameterized constructor`(){
        val movements = arrayOf(arrayOf(1,0), arrayOf(0,1))
        val card = Card("blue_knight",2,movements,"res/drawable/Knights/blue_knight.png")
        assertEquals("Name should be blue_knight","blue_knight",card.name)
        assertEquals("Movement count should be 2",2,card.movementCount)
        assertArrayEquals("Movements should match the inputs", movements, card.movements)
    }

    //Tests the toString Method
    @Test
    fun `test to String Method`(){
        val movements = arrayOf(arrayOf(1,0), arrayOf(0,1))
        val card = Card("blue_knight",2,movements,"res/drawable/Knights/blue_knight.png")
        val expectedString = "Name: blue_knight \nBewegungsanzahl: 2 \nBewegungen: ${movements.contentDeepToString()} \nBild-URL: res/drawable/Knights/blue_knight.png"
        assertEquals("toString output should match the expected string", expectedString, card.toString())
    }

    //Tests the movements array
    @Test
    fun `test movements Array`(){
        val movements = arrayOf(arrayOf(0,1), arrayOf(1,0))
        val card = Card("blue_knight", 2,movements,"res/drawable/Knights/blue_knight.png")
        assertArrayEquals("First movement should be [0,1]", arrayOf(0,1), card.movements[0])
        assertArrayEquals("Second movement should be [1,0]", arrayOf(1,0),card.movements[1])
    }
}