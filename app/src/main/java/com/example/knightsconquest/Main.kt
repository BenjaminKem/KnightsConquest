import com.example.knightsconquest.*

fun main() {
    // Erstelle eine Instanz der GameBoard-Klasse
    val gameBoard = GameBoard()

    // Initialisiere das Spielbrett
    gameBoard.init()
    gameBoard.printBoard()
    val cardCreator = CardCreator()
    val fiveRandomCards = cardCreator.getFiveRandomCards()
}
