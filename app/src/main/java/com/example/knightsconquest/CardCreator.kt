import android.content.Context
import com.example.knightsconquest.Card
import com.example.knightsconquest.R
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream
import java.util.Random

class CardCreator(private val context: Context) {
    private fun createCardList(): List<Card> {
        val objectMapper = jacksonObjectMapper()
        val inputStream: InputStream = context.resources.openRawResource(R.raw.cards)
        val jsonData = inputStream.bufferedReader().use { it.readText() }
        val cards: List<Card> = objectMapper.readValue(jsonData)
        println("Kartenliste wurde erstellt")
        return cards
    }

    fun getFiveRandomCards(): MutableList<Card> {
        val random = Random()
        val cardList = createCardList()
        val fiveRandomCards = mutableListOf<Card>()
        val alreadyUsedRandomNumbers = mutableListOf<Int>()
        repeat(5) {
            var randomIndex: Int
            do {
                randomIndex = random.nextInt(cardList.size)
            } while (alreadyUsedRandomNumbers.contains(randomIndex) || randomIndex < 0)

            alreadyUsedRandomNumbers.add(randomIndex)
            fiveRandomCards.add(cardList[randomIndex])
        }
        println("5 Random Karten wurden ausgewählt")
        return fiveRandomCards
    }
}
