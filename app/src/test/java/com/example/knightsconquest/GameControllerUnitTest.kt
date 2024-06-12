package com.example.knightsconquest

import CardCreator
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

class GameControllerUnitTest {

    private lateinit var controller: GameController
    private lateinit var testCardCreator: CardCreator
    private lateinit var testGameBoard: GameBoard

    @Before
    fun setUp() {
        testGameBoard = mock()
        testCardCreator = mock()
        controller = GameController()
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `test If BlueCardIndexInvalid`() {
        controller.getBlueCard(10)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `test If RedCardIndexInvalid`() {
        controller.getRedCard(10)
    }
    @RunWith(MockitoJUnitRunner::class)
    class GameBoardTest {

        @Mock
        lateinit var testCardCreator: CardCreator

        @Mock
        lateinit var testGameBoard: GameBoard

        @InjectMocks
        lateinit var controller: GameController

        @Before
        fun setup() {
            MockitoAnnotations.initMocks(this)
        }

        @Test
        fun `test Initialize GameBoard`() {
            val testCards = mutableListOf(
                Card("Card1", 1, arrayOf(arrayOf(1, 0)), ""),
                Card("Card2", 1, arrayOf(arrayOf(1, 0)), ""),
                Card("Card3", 1, arrayOf(arrayOf(1, 0)), ""),
                Card("Card4", 1, arrayOf(arrayOf(1, 0)), ""),
                Card("Card5", 1, arrayOf(arrayOf(1, 0)), "")
            )

            whenever(testCardCreator.getFiveRandomCards()).thenReturn(testCards)

            controller.startGame()

            verify(testGameBoard).init()
            verify(testCardCreator).getFiveRandomCards()

            assertEquals(2, controller.redCards.size)
            assertEquals(2, controller.blueCards.size)
            assertEquals(1, controller.stack.size)
        }
    }

    @Test
    fun `test getBlueCard valid index`() {
        val mockCard = Card("BlueCard", 1, arrayOf(arrayOf(1, 0)), "")
        controller.blueCards.add(mockCard)
        val result = controller.getBlueCard(0)
        assertEquals(mockCard, result)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `test BlueCard invalid index`() {
        controller.getBlueCard(0)
    }

    @Test
    fun `test getRedCard valid index`() {
        val mockCard = Card("RedCard", 1, arrayOf(arrayOf(1, 0)), "")
        controller.redCards.add(mockCard)
        val result = controller.getRedCard(0)
        assertEquals(mockCard, result)
    }

    @Test
    fun `test getStackCard`() {
        val mockCard = Card("StackCard", 1, arrayOf(arrayOf(1, 0)), "")
        controller.stack.add(mockCard)
        val result = controller.getStackCard()
        assertEquals(mockCard, result)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `test getStackCard when stack is empty`() {
        controller.getStackCard()
    }
}
