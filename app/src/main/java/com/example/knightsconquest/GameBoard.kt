package com.example.knightsconquest



class GameBoard {
    private val size = 5
    private val board = Array(size) { Array(size) { Tile(TileColor.NEUTRAL, FigureType.NONE) } }

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

    fun isValidMove(cardname: String, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        // Überprüft, ob der Zug von (fromX, fromY) nach (toX, toY) gültig ist.

        val clickedPiece = this.getPieceAt(fromX, fromY)
        val destinationPiece = this.getPieceAt(toX, toY)
        var movementArray = checkCardMovement(cardname,fromX, fromY, toX, toY)

        //Bewegung außerhalb des Spielfeldes ist nicht möglich
        if(toX >= 5  || toY >= 5){
            return false;
        }
        else if (toX < 0 ||  toY < 0){
            return false;
        }
        //Bewegung auf ein Feld mit der selben Farbe ist nicht möglich
        else if(clickedPiece.color == destinationPiece.color){
            return false
        }
        else if(!(checkCardMovement(cardname,fromX, fromY, toX, toY))){
            return false
        }
        return true
    }
    fun checkCardMovement(cardname: String, fromX: Int, fromY: Int, toX: Int, toY: Int):Boolean{
        val cardCreator = CardCreator()
        var cardList = cardCreator.createCardList()

        //Wenn der Kartenname nicht in der Liste vorhanden ist ist die Bewegung ungültig
        if(!(cardList.any{it.name == cardname})){
            return false
        }

        var cardIndex = cardList.indexOfFirst{it.name == cardname}
        val card = cardList[cardIndex]

        for(movement in card.movements){
            val xMovement = movement[0]
            val yMovement = movement[1]

            if(fromX + xMovement != toX || fromY+yMovement != toY){
                return false
            }
        }

        return true
    }
    fun makeMove(cardname: String,fromX: Int, fromY: Int, toX: Int, toY: Int) {
        // Führt den Zug von (fromX, fromY) nach (toX, toY) aus.
        // Aktualisiere das Spielbrett und die Spielsteine.
        if(isValidMove(cardname,fromX,fromY,toX,toY)){

            val tempTile = Tile((this.getPieceAt(fromX, fromY)).color, (this.getPieceAt(fromX, fromY)).figure)
            //löschen an alter Position
            board[fromX][fromY] =Tile(TileColor.NEUTRAL, FigureType.NONE)
            //einsetzen an neuer Position
            board[toX][toY] = tempTile
        }
    }
    fun didSomeoneWin(){

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