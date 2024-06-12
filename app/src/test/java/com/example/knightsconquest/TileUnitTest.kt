package com.example.knightsconquest


import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TileUnitTest {

    @Test
    fun `test default constructor`() {
        val tile = Tile()
        assertNull("Tile color should be null", tile.color)
        assertNull("Tile figure should be null", tile.figure)
    }

    @Test
    fun `test all-args constructor`() {
        val tile = Tile(TileColor.RED, FigureType.KING)
        assertEquals("Tile color should be RED", TileColor.RED, tile.color)
        assertEquals("Tile figure should be KING", FigureType.KING, tile.figure)
    }

    @Test
    fun `test color setter and getter`() {
        val tile = Tile()
        tile.color = TileColor.BLUE
        assertEquals("Tile color should be BLUE", TileColor.BLUE, tile.color)
    }

    @Test
    fun `test figure setter and getter`() {
        val tile = Tile()
        tile.figure = FigureType.KNIGHT
        assertEquals("Tile figure should be KNIGHT", FigureType.KNIGHT, tile.figure)
    }

    @Test
    fun `test toString method`() {
        val tile = Tile(TileColor.RED, FigureType.KING)
        val expectedString = "Tile(color=RED, figure=KING)"
        assertEquals("Tile's toString should match", expectedString, tile.toString())
    }

    @Test
    fun `test equals and hashCode`() {
        val tile1 = Tile(TileColor.RED, FigureType.KING)
        val tile2 = Tile(TileColor.RED, FigureType.KING)
        val tile3 = Tile(TileColor.BLUE, FigureType.KNIGHT)

        assertTrue("Tiles with same properties should be equal", tile1 == tile2)
        assertFalse("Tiles with different properties should not be equal", tile1 == tile3)
        assertEquals("Hashcodes of equal tiles should be equal", tile1.hashCode(), tile2.hashCode())
        assertNotEquals("Hashcodes of different tiles should not be equal", tile1.hashCode(), tile3.hashCode())
    }

    @Test
    fun `test copy method`() {
        val tile1 = Tile(TileColor.RED, FigureType.KING)
        val tile2 = tile1.copy()

        assertTrue("Copied tile should be equal to original", tile1 == tile2)
        assertEquals("Copied tile's color should match original", tile1.color, tile2.color)
        assertEquals("Copied tile's figure should match original", tile1.figure, tile2.figure)

        val tile3 = tile1.copy(color = TileColor.BLUE)
        assertNotEquals("Modified copy should not equal original", tile1, tile3)
        assertEquals("Modified copy should have new color", TileColor.BLUE, tile3.color)
        assertEquals("Modified copy should have same figure as original", tile1.figure, tile3.figure)
    }
}
