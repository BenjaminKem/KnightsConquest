import com.example.knightsconquest.*

fun main() {
    val gameController = GameController()
    gameController.startGame()
    print(gameController.blueCards[0].name)
}
