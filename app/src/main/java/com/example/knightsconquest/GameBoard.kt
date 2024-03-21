package com.example.knightsconquest



class GameBoard {
    private val size = 5
    private val board = Array(size) { Array(size) { Tile(TileColor.NEUTRAL, FigureType.NONE) } }
    private var redWon = false;
    private var blueWon = false;
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

    fun isValidMove(fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        // Überprüft, ob der Zug von (fromX, fromY) nach (toX, toY) gültig ist.

        val clickedPiece = this.getPieceAt(fromX, fromY)

        val destinationPiece = this.getPieceAt(toX, toY)

        //Bewegung außerhalb des Spielfeldes ist nicht möglich
        if(toX >= 5  || toY >= 5){
            return false;
        }
        else if (toX < 0 || toY < 0){
            return false;
        }
        //Bewegung auf ein Feld mit der selben Farbe ist nicht möglich
        else if(clickedPiece.color == destinationPiece.color){
            return false
        }
        return true
    }
    fun makeMove(fromX: Int, fromY: Int, toX: Int, toY: Int) {
        // Führt den Zug von (fromX, fromY) nach (toX, toY) aus.
        // Aktualisiere das Spielbrett und die Spielsteine.
        if(isValidMove(fromX,fromY,toX,toY)){
            val tempTile = Tile((this.getPieceAt(fromX, fromY)).color, (this.getPieceAt(fromX, fromY)).figure)
            //löschen an alter Position
            board[fromX][fromY] =Tile(TileColor.NEUTRAL, FigureType.NONE)
            //einsetzen an neuer Position
            board[toX][toY] = tempTile
        }
        didSomeoneWin()
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
}