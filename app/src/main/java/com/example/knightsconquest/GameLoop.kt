package com.example.knightsconquest

class GameLoop {
    fun playGame(){
        // Erstelle eine Instanz der GameBoard-Klasse
        val gameBoard = GameBoard()

        // Initialisiere das Spielbrett und generiere die benötigten Karten
        gameBoard.init()
        val cardCreator = CardCreator()
        val fiveRandomCards = cardCreator.getFiveRandomCards()

        val cardNames = mutableListOf<String>()
        for (card in fiveRandomCards) {
            cardNames.add(card.name)
        }
        println("Die Namen der generierten Karten sind: ${cardNames.joinToString(", ")}")

        var iterator = 0
        gameBoard.printBoard()

        while(!(gameBoard.didSomeoneWin())){
            println("Gib deine Bewegung ein:")
            var input = readlnOrNull()
            var movementCmd = input?.split(" ")

            if(iterator >= fiveRandomCards.size){
                iterator = 0
            }

            //Bewegung wird durch geführt wenn die Werte richtig sind
            if(input != null){
                if (movementCmd?.size == 4){
                    val startX = movementCmd[0].toInt()
                    val startY = movementCmd[1].toInt()
                    val destinationX = movementCmd[2].toInt()
                    val destinationY = movementCmd[3].toInt()

                    gameBoard.makeMove(fiveRandomCards[iterator], startX, startY, destinationX, destinationY)
                }else{
                    println("Ungültige Bewegung: Eine Bewegung besteht aus vier Koordinaten")
                }
                gameBoard.printBoard()
            }else{
                println("Es wurde keine Bewegung eingegeben")
            }
            iterator++
        }
    }
}