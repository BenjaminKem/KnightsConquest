import android.content.Context
import android.util.Log
import com.example.knightsconquest.Card
import com.example.knightsconquest.R
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.snapshots
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import java.util.Random


open class CardCreator() {
    private fun createCardList(): List<Card> {
        val objectMapper = jacksonObjectMapper()
        val jsonTestData = "[{\"name\":\"elefant\",\"movementCount\":4,\"movements\":[[-1,-1],[-1,1],[0,1],[0,-1]],\"imageUrl\":\"@drawable/elefantmovement\"},{\"name\":\"boar\",\"movementCount\":3,\"movements\":[[-1,0],[0,-1],[0,1]],\"imageUrl\":\"@drawable/boarmovement\"},{\"name\":\"cobra\",\"movementCount\":3,\"movements\":[[-1,1],[0,-1],[1,1]],\"imageUrl\":\"@drawable/cobramovement\"},{\"name\":\"crab\",\"movementCount\":3,\"movements\":[[-1,0],[0,-2],[0,2]],\"imageUrl\":\"@drawable/crabmovement\"},{\"name\":\"crane\",\"movementCount\":3,\"movements\":[[-1,0],[1,-1],[1,1]],\"imageUrl\":\"@drawable/cranemovement\"},{\"name\":\"dragon\",\"movementCount\":4,\"movements\":[[1,1],[1,-1],[-1,2],[-1,-2]],\"imageUrl\":\"@drawable/dragonmovement\"},{\"name\":\"eel\",\"movementCount\":3,\"movements\":[[0,1],[1,-1],[-1,-1]],\"imageUrl\":\"@drawable/eelmovement\"},{\"name\":\"frog\",\"movementCount\":3,\"movements\":[[0,-2],[-1,-1],[1,1]],\"imageUrl\":\"@drawable/frogmovement\"},{\"name\":\"goose\",\"movementCount\":4,\"movements\":[[0,1],[0,-1],[-1,-1],[1,1]],\"imageUrl\":\"@drawable/goosemovement\"},{\"name\":\"horse\",\"movementCount\":3,\"movements\":[[0,-1],[1,0],[-1,0]],\"imageUrl\":\"@drawable/horsemovement\"},{\"name\":\"mantis\",\"movementCount\":3,\"movements\":[[-1,-1],[-1,1],[1,0]],\"imageUrl\":\"@drawable/mantismovement\"},{\"name\":\"monkey\",\"movementCount\":4,\"movements\":[[1,1],[1,-1],[-1,-1],[-1,1]],\"imageUrl\":\"@drawable/monkeymovement\"},{\"name\":\"ox\",\"movementCount\":3,\"movements\":[[0,1],[-1,0],[1,0]],\"imageUrl\":\"@drawable/oxmovement\"},{\"name\":\"rabbit\",\"movementCount\":3,\"movements\":[[1,-1],[-1,1],[0,2]],\"imageUrl\":\"@drawable/rabbitmovement\"},{\"name\":\"rooster\",\"movementCount\":4,\"movements\":[[0,1],[0,-1],[1,-1],[-1,1]],\"imageUrl\":\"@drawable/roostermovement\"},{\"name\":\"tiger\",\"movementCount\":2,\"movements\":[[1,0],[-2,0]],\"imageUrl\":\"@drawable/tigermovement\"},{\"name\":\"bear\",\"movementCount\":3,\"movements\":[[-1,0],[-1,-1],[1,1]],\"imageUrl\":\"@drawable/beatmovement\"},{\"name\":\"cat\",\"movementCount\":4,\"movements\":[[-1,0],[0,-1],[0,2],[2,0]],\"imageUrl\":\"@drawable/catmovement\"},{\"name\":\"centipede\",\"movementCount\":3,\"movements\":[[-1,0],[0,-1],[2,2]],\"imageUrl\":\"@drawable/centipedemovement\"},{\"name\":\"dog\",\"movementCount\":3,\"movements\":[[-1,-1],[0,-1],[1,-1]],\"imageUrl\":\"@drawable/dogmovement\"},{\"name\":\"fox\",\"movementCount\":3,\"movements\":[[-1,1],[0,1],[1,1]],\"imageUrl\":\"@drawable/foxmovement\"},{\"name\":\"giraffe\",\"movementCount\":3,\"movements\":[[1,0],[-1,-2],[-1,2]],\"imageUrl\":\"@drawable/giraffemovement\"},{\"name\":\"goat\",\"movementCount\":3,\"movements\":[[0,-1],[1,0],[-1,1]],\"imageUrl\":\"@drawable/goatmovement\"},{\"name\":\"hornet\",\"movementCount\":3,\"movements\":[[-1,0],[0,1],[2,-2]],\"imageUrl\":\"@drawable/hornetmovement\"},{\"name\":\"iguna\",\"movementCount\":3,\"movements\":[[-1,0],[-1,-2],[1,1]],\"imageUrl\":\"@drawable/igunamovement\"},{\"name\":\"kirin\",\"movementCount\":3,\"movements\":[[-2,-1],[-2,1],[2,0]],\"imageUrl\":\"@drawable/kirinmovement\"},{\"name\":\"lobster\",\"movementCount\":4,\"movements\":[[-1,-1],[-1,1],[2,-1],[2,1]],\"imageUrl\":\"@drawable/lobstermovement\"},{\"name\":\"mouse\",\"movementCount\":3,\"movements\":[[-1,0],[0,1],[1,-1]],\"imageUrl\":\"@drawable/mousemovement\"},{\"name\":\"otter\",\"movementCount\":3,\"movements\":[[-1,-1],[1,1],[0,2]],\"imageUrl\":\"@drawable/ottermovement\"},{\"name\":\"panda\",\"movementCount\":3,\"movements\":[[-1,0],[-1,1],[1,-1]],\"imageUrl\":\"@drawable/pandamovement\"},{\"name\":\"phoenix\",\"movementCount\":4,\"movements\":[[-1,1],[-1,-1],[0,-2],[0,2]],\"imageUrl\":\"@drawable/phoenixmovement\"},{\"name\":\"rat\",\"movementCount\":3,\"movements\":[[-1,0],[0,-1],[1,1]],\"imageUrl\":\"@drawable/ratmovement\"},{\"name\":\"sable\",\"movementCount\":3,\"movements\":[[-1,1],[1,-1],[0,-2]],\"imageUrl\":\"@drawable/sablemovement\"},{\"name\":\"seasnake\",\"movementCount\":3,\"movements\":[[-1,0],[0,2],[1,-1]],\"imageUrl\":\"@drawable/seasnakemovement\"},{\"name\":\"serow\",\"movementCount\":4,\"movements\":[[-1,0],[0,1],[2,0],[0,-2]],\"imageUrl\":\"@drawable/serowmovement\"},{\"name\":\"sheep\",\"movementCount\":3,\"movements\":[[-1,-1],[0,1],[1,0]],\"imageUrl\":\"@drawable/sheepmovement\"},{\"name\":\"steer\",\"movementCount\":4,\"movements\":[[0,-1],[0,1],[1,1],[1,-1]],\"imageUrl\":\"@drawable/steermovement\"},{\"name\":\"tanuki\",\"movementCount\":3,\"movements\":[[-1,0],[-1,2],[1,-1]],\"imageUrl\":\"@drawable/tanukimovement\"},{\"name\":\"turtle\",\"movementCount\":4,\"movements\":[[1,1],[1,-1],[0,-2],[0,2]],\"imageUrl\":\"@drawable/turtlemovement\"},{\"name\":\"viper\",\"movementCount\":3,\"movements\":[[-1,0],[0,-2],[1,1]],\"imageUrl\":\"@drawable/vipermovement\"}]"
        val cards: List<Card> = objectMapper.readValue(jsonTestData)
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
