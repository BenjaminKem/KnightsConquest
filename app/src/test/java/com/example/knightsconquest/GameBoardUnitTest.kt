package com.example.knightsconquest


import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class GameBoardUnitTest {

    private lateinit var gameBoard: GameBoard
    @Before
    fun setup(){
        gameBoard = GameBoard()
        gameBoard.init()
    }

    //Tests the game board initialized
    @Test
    fun `test board init`(){
        val expectedRedKing = Tile(TileColor.RED,FigureType.KING)
        val expectedBlueKing = Tile(TileColor.BLUE,FigureType.KING)
        val expectedRedKnight = Tile(TileColor.RED,FigureType.KNIGHT)
        val expectedBlueKnight = Tile(TileColor.BLUE,FigureType.KNIGHT)

        assertEquals("The Piece should be a Red King",expectedRedKing, gameBoard.getPieceAt(0, 2))
        assertEquals("The Piece should be a Blue King",expectedBlueKing, gameBoard.getPieceAt(4, 2))
        assertEquals("The Piece should be a Red Knight",expectedRedKnight, gameBoard.getPieceAt(0, 0))
        assertEquals("The Piece should be a Blue Knight",expectedBlueKnight, gameBoard.getPieceAt(4, 0))
    }

    //Tests the getPieceAt Method for correct Color and Type
    @Test
    fun `test getPieceAt`(){
        val tile = gameBoard.getPieceAt(0,2)
        assertEquals("The Tile color should be red",TileColor.RED,tile.color)
        assertEquals("The Figure Type of the Tile should be a King",FigureType.KING, tile.figure)
    }

    //Tests the isValidMove using a test card
    @Test
    fun `test isValidMove`(){
        val card = Card("blue_knight", 1, arrayOf(arrayOf(-1,0)),"res/drawable/Knights/blue_knight.png")
        val validMove = gameBoard.isValidMove(card,4,2,3,2)
        assertTrue("The move is invalid",validMove)
    }

    //Tests the isValidMove for an out of bounds move
    @Test
    fun `test invalid move out of bounds`(){
        val card = Card("blue_knight", 1, arrayOf(arrayOf(1,0)),"res/drawable/Knights/blue_knight.png")
        val invalidMove = gameBoard.isValidMove(card,4,4,5,4)
        assertFalse("The move should be within the bounds of the game board" ,invalidMove)
    }

    //Tests the isValidMove for a move onto a tile of the same color
    @Test
    fun `test invalid move onto same color tile`(){
        val card = Card("blue_knight", 1, arrayOf(arrayOf(-1,0)),"res/drawable/Knights/blue_knight.png")
        val invalidMove = gameBoard.isValidMove(card,4,0,3,0)
        assertTrue("The move should be onto an empty field",invalidMove)
    }

    //Tests the makeMove Method to make a valid move
    @Test
    fun `test makeMove valid`(){
        val card = Card("blue_knight", 1, arrayOf(arrayOf(-1,0)),"res/drawable/Knights/blue_knight.png")
        val moveMade = gameBoard.makeMove(card,4,2,3,2)
        assertTrue("This move is not possible",moveMade)
        assertEquals(Tile(TileColor.BLUE,FigureType.KING),gameBoard.getPieceAt(3,2))
        assertEquals(Tile(TileColor.NEUTRAL,FigureType.NONE), gameBoard.getPieceAt(4,2))
    }

    //Tests for an invalid move with makeMove method
    @Test
    fun `test makeMove invalid`(){
        val card = Card("blue_knight", 1, arrayOf(arrayOf(1,0)),"res/drawable/Knights/blue_knight.png")
        val moveMade = gameBoard.makeMove(card,4,4,5,4)
        assertFalse("This move is not valid",moveMade)
        assertEquals(Tile(TileColor.BLUE,FigureType.KNIGHT),gameBoard.getPieceAt(4,4))
    }

    //Tests the endTurn method to correctly change the turns
    @Test
    fun `test endTurn`(){
        gameBoard.turnIndicator = TileColor.RED
        gameBoard.endTurn()
        assertEquals("It should be Blues turn now",TileColor.BLUE,gameBoard.turnIndicator)

        gameBoard.endTurn()
        assertEquals("It Should be Red's turn now",TileColor.RED,gameBoard.turnIndicator)
    }

    //Tests the didSomeoneWin method for a winner
    @Test
    fun `test did someone win`(){
        gameBoard.board[0][2] = Tile(TileColor.BLUE,FigureType.KING)
        val hasWon = gameBoard.didSomeoneWin()
        assertTrue("Game should've ended",hasWon)
        assertTrue("Blue should be the winner",gameBoard.blueWon)
    }

    //Tests the didSomeoneWin and red-/blueWon
    @Test
    fun `test did someone not win`(){
        val noWin = gameBoard.didSomeoneWin()
        assertFalse(noWin)
        assertFalse(gameBoard.redWon)
        assertFalse(gameBoard.blueWon)
    }
}