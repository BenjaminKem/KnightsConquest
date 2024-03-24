package com.example.knightsconquest
//TODO muss noch den Code aufräumen und gegebenenfalls refactorn nach dem die Änderungen funktioniert haben

class GameLoop {
    fun playGame(){
        // Erstelle eine Instanz der GameBoard-Klasse
        val gameBoard = GameBoard()

        // Initialisiere das Spielbrett und generiere die benötigten Karten
        gameBoard.init()
        val cardCreator = CardCreator()
        val fiveRandomCards = cardCreator.getFiveRandomCards()

        //Die drei Kartenlisten werden hier generiert
        val redCards = mutableListOf<Card>()
        val blueCards = mutableListOf<Card>()
        val stack = mutableListOf<Card>()

        //Aufteilen der Kartenliste in Farben
        redCards.addAll(fiveRandomCards.take(2))
        blueCards.addAll(fiveRandomCards.slice(2 until 4))
        stack.addAll(fiveRandomCards.takeLast(1))

        gameBoard.printBoard()

        while(!(gameBoard.didSomeoneWin())){
            if(gameBoard.turnIndicator == TileColor.RED){
                println("Rote Karten: ${redCards.joinToString { it.name }}")
                println("Stapel: ${stack[0].name}")
            }else{
                println("Blaue Karten: ${blueCards.joinToString { it.name }}")
                println("Stapel: ${stack[0].name}")
            }

            //Eine der generierten Karten wird ausgewählt
            println("Wähle eine Karte aus:")
            var selectedCard = readlnOrNull()
            var removedCard: Card? = null

            // Zug für Rot, nur auf rote Karten zugreifen und die Karten rotieren
            if (gameBoard.turnIndicator == TileColor.RED) {
                val redIndex = redCards.indexOfFirst { it.name == selectedCard }
                if (redIndex != -1) {
                    removedCard = redCards[redIndex]
                    redCards.removeAt(redIndex)
                    stack.add(0, removedCard)
                }

            } else if (gameBoard.turnIndicator == TileColor.BLUE) {
                // Zug für Blau, nur auf blaue Karten zugreifen und die Karten rotieren
                val blueIndex = blueCards.indexOfFirst { it.name == selectedCard }
                if (blueIndex != -1) {
                    removedCard = stack[blueIndex]
                    stack.removeAt(blueIndex)
                    blueCards.add(0, removedCard)
                }

            }

            //Nach der Auswahl der Karte muss die dazugehörige Bewegung ausgewählt werden
            println("Gib deine Bewegung ein:")
            var input = readlnOrNull()
            var movementCmd = input?.split(" ")


            //Bewegung wird durch geführt wenn die Werte richtig sind
            if(input != null){
                if (movementCmd?.size == 4){
                    val startX = movementCmd[0].toInt()
                    val startY = movementCmd[1].toInt()
                    val destinationX = movementCmd[2].toInt()
                    val destinationY = movementCmd[3].toInt()

                    if (removedCard != null) {
                        gameBoard.makeMove(removedCard, startX, startY, destinationX, destinationY)
                        //TODO Es soll nur dann aus den Farblisten entnommen werden wenn die bewegung erfolgreich war
                    }
                }else{
                    println("Ungültige Bewegung: Eine Bewegung besteht aus vier Koordinaten")
                }

                gameBoard.printBoard()
            }else{
                println("Es wurde keine Bewegung eingegeben")
            }
        }
    }
}