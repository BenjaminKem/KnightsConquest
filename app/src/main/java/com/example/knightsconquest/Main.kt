import com.example.knightsconquest.*

fun main() {
    // Erstelle eine Instanz der GameBoard-Klasse
    val gameBoard = GameBoard()

    // Initialisiere das Spielbrett
    gameBoard.init()
    gameBoard.printBoard()
    val cardCreator = CardCreator()
    val card = cardCreator.createCardList().get(0)
    println(card)
    gameBoard.makeMove(card,4,2,2,2)
    gameBoard.printBoard()
    val fiveRandomCards = cardCreator.getFiveRandomCards()
}
