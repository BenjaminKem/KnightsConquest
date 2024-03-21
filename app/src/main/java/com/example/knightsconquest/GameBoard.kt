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

    fun isValidMove(fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        // Überprüft, ob der Zug von (fromX, fromY) nach (toX, toY) gültig ist.
        // Implementiere die Logik für die gültigen Züge in Onitama.
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