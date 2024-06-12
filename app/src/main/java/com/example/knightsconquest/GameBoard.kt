package com.example.knightsconquest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class GameBoard {
    var size = 5
    var board = Array(size) { Array(size) { Tile(TileColor.NEUTRAL, FigureType.NONE) } }
    var turnIndicator : TileColor = TileColor.BLUE
    var redWon = false
    var blueWon = false
    data class GameBoard(
        var size: Int,
        var board: Array<Array<Tile>>,
        var turnIndicator: TileColor,
        var redWon: Boolean,
        var blueWon: Boolean
    )
    fun init (){
        for(count in 0..4){
            if(count == 2){
                board[0][count] = Tile(TileColor.RED,FigureType.KING)
                board[4][count] = Tile(TileColor.BLUE,FigureType.KING)
            }else{
                board[0][count] = Tile(TileColor.RED,FigureType.KNIGHT)
                board[4][count] = Tile(TileColor.BLUE,FigureType.KNIGHT)
            }
        }
    }

    fun getPieceAt(x: Int, y: Int): Tile {
        // Gibt den Spielstein an der Position (x, y) zurück.
        return board[x][y]
    }

    fun isValidMove(card: Card, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        // Überprüft, ob der Zug von (fromX, fromY) nach (toX, toY) gültig ist.

        //Bewegung außerhalb des Spielfeldes ist nicht möglich
        if(toX >= 5  || toY >= 5){
            println("Bewegung verlässt das Feld")
            return false
        }
        else if (toX < 0 ||  toY < 0){
            println("Bewegung verlässt das Feld")
            return false
        }

        val clickedPiece = this.getPieceAt(fromX, fromY)
        val destinationPiece = this.getPieceAt(toX, toY)


        //Bewegung auf ein Feld mit der selben Farbe ist nicht möglich
        if(clickedPiece.color == destinationPiece.color){
            println("Du kannst nicht deinen eigene Figur schlagen")
            return false
        }
        else if(clickedPiece.color != turnIndicator){
            println("Andere Farbe ist dran")
            return false
        }
        else if(!(checkCardMovement(card,fromX, fromY, toX, toY))){
            println("Bewegung passt nicht zur Karte")
            return false
        }
        return true
    }
    private fun checkCardMovement(card: Card, fromX: Int, fromY: Int, toX: Int, toY: Int):Boolean{
        card.movements[0][0]
        for(movementPatternCounter in 0 until card.movementCount){
            var xMovement = card.movements[movementPatternCounter][0]
            var yMovement = card.movements[movementPatternCounter][1]

            if(turnIndicator == TileColor.RED){
                xMovement *= -1
                yMovement *= -1
            }
            if(fromX + xMovement == toX && fromY+yMovement == toY){
                return true
            }
        }
        return false
    }
    fun makeMove(card: Card,fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        // Führt den Zug von (fromX, fromY) nach (toX, toY) aus.
        // Aktualisiere das Spielbrett und die Spielsteine.
        if(isValidMove(card,fromX,fromY,toX,toY)){
            val tempTile = Tile((this.getPieceAt(fromX, fromY)).color, (this.getPieceAt(fromX, fromY)).figure)
            //löschen an alter Position
            board[fromX][fromY] =Tile(TileColor.NEUTRAL, FigureType.NONE)
            //einsetzen an neuer Position
            board[toX][toY] = tempTile
            didSomeoneWin()
            return true
        }
        return false
    }
    fun didSomeoneWin() : Boolean{
        //zum tracken ob die Könige noch leben
        var blueKingAlive = false
        var redKingAlive = false
        for (rowCounter in 0..4) {
            for (columnCounter in 0..4) {
                //Wenn der Blaue König in der Roten Base ist hat Blau gewonnen
                if(rowCounter == 0 && columnCounter== 2 && getPieceAt(rowCounter, columnCounter).figure == FigureType.KING && getPieceAt(rowCounter, columnCounter).color == TileColor.BLUE){
                    println("Blue Won")
                    blueWon = true
                    return true
                }
                //Wenn der Rote König in der Blauen Base ist hat Rot gewonnen
                else if(rowCounter == 4 && columnCounter== 2 && getPieceAt(rowCounter, columnCounter).figure == FigureType.KING && getPieceAt(rowCounter, columnCounter).color == TileColor.RED){
                    println("Red Won")
                    redWon = true
                    return true
                }
                //Wenn der Rote oder Blaue König gefunden wird wird der Wert auf True gesetzt um am Ende zu Prüfen ob beide noch leben
                else if(getPieceAt(rowCounter, columnCounter).figure == FigureType.KING && getPieceAt(rowCounter, columnCounter).color == TileColor.BLUE){
                    blueKingAlive = true
                }else if(getPieceAt(rowCounter, columnCounter).figure == FigureType.KING && getPieceAt(rowCounter, columnCounter).color == TileColor.RED){
                    redKingAlive = true
                }
            }
        }
        //Wenn beide Leben geht das Spiel weiter
        if(redKingAlive && blueKingAlive){
            return false
        }
        //Wenn der Rote König überlebt hat dann hat gewinnt Rot sont Blau
        if(redKingAlive){
            println("Red Won")
            redWon = true
        }
        println("Blue Won")
        blueWon = true
        return true
    }
    fun printBoard(){
        for (rowCounter in 0..4) {
            for (columnCounter in 0..4) {
                if(this.getPieceAt(rowCounter,columnCounter).color == TileColor.RED && this.getPieceAt(rowCounter,columnCounter).figure == FigureType.KING){
                    print("RK \t")
                }
                else if(this.getPieceAt(rowCounter,columnCounter).color == TileColor.BLUE && this.getPieceAt(rowCounter,columnCounter).figure == FigureType.KING){
                    print("BK \t")
                }
                else if(this.getPieceAt(rowCounter,columnCounter).color == TileColor.BLUE && this.getPieceAt(rowCounter,columnCounter).figure == FigureType.KNIGHT){
                    print("BS \t")
                }
                else if(this.getPieceAt(rowCounter,columnCounter).color == TileColor.RED && this.getPieceAt(rowCounter,columnCounter).figure == FigureType.KNIGHT){
                    print("RS \t" )
                }
                else{
                    print("N \t")
                }
            }
            println()
        }
    }
    fun endTurn(){
        turnIndicator = if (turnIndicator == TileColor.RED){
            println("Blue Turn")
            TileColor.BLUE
        }else{
            println("Red Turn")
            TileColor.RED
        }
    }
}