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

        /*val cardNames = mutableListOf<String>()
        for (card in fiveRandomCards) {
            cardNames.add(card.name)
        }
        println("Die Namen der generierten Karten sind: ${cardNames.joinToString(", ")}")*/

        var iterator = 0
        gameBoard.printBoard()

        while(!(gameBoard.didSomeoneWin())){
            //Eine der generierten Karten wird ausgewählt
            println("Wähle eine Karte aus:")
            var selectedCard = readlnOrNull()
            var removedCard: Card

            // Zug für Rot, nur auf rote Karten zugreifen
            //TODO ich muss hier einiges noch überarbeiten und fertigstellen vllt. chat fragen
            if (gameBoard.turnIndicator == TileColor.RED) {
                for(card in redCards){
                    if(selectedCard == redCards[0].name)
                        removedCard = redCards[card]
                        break
                }

                removedCard = redCards[0]
            } else if (gameBoard.turnIndicator == TileColor.BLUE) {
                // Zug für Blau, nur auf blaue Karten zugreifen
                for(card in blueCards){
                    if(selectedCard == blueCards[0].name){
                        removedCard = blueCards[card]
                        break
                    }
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

                    //TODO hier muss als Übergabeparameter noch die "removedCard" eingefügt werden und berichtigen
                    gameBoard.makeMove(removedCard, startX, startY, destinationX, destinationY)
                }else{
                    println("Ungültige Bewegung: Eine Bewegung besteht aus vier Koordinaten")
                }

                //Drei Kartenlisten
                //TODO ich muss das mit dem Anzeigen richtig machen und das muss noch an die richtige Stelle kommen
                println("Rote Karten: ${redCards.joinToString { it.name }}")
                println("Blaue Karten: ${blueCards.joinToString { it.name }}")
                println("Stapel: ${stack[0].name}")

                gameBoard.printBoard()
            }else{
                println("Es wurde keine Bewegung eingegeben")
            }
        }
    }
}