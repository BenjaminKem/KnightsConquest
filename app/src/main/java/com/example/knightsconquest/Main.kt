import com.example.knightsconquest.*

fun main() {
    // Erstelle eine Instanz der GameBoard-Klasse
    val gameBoard = GameBoard()

    // Initialisiere das Spielbrett
    gameBoard.init()

    gameBoard.printBoard()
    println()
    println()
    gameBoard.makeMove(0,0,1,0)

    gameBoard.printBoard()

    val cardCreator = CardCreator()
    cardCreator.createCardList()
}
