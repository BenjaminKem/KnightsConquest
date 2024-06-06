import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.SecureRandom

class GameIdGenerator {
        private val ID_LENGTH = 5
        private val CHARACTERS = "0123456789"
        private val random = SecureRandom()
        private val database = FirebaseDatabase.getInstance().reference

        fun generateUniqueGameId(callback: (String) -> Unit) {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            if (currentUser == null) {
                callback("User not authenticated")
                return
            }

            val id = StringBuilder(ID_LENGTH)
            repeat(ID_LENGTH) {
                val index = random.nextInt(CHARACTERS.length)
                id.append(CHARACTERS[index])
            }
            val gameId = id.toString()
            checkIfGameIdExists(gameId) { exists ->
                if (exists) {
                    generateUniqueGameId(callback)
                } else {
                    callback(gameId)
                }
            }
        }

        private fun checkIfGameIdExists(gameId: String, callback: (Boolean) -> Unit) {
            val gameIdReference = database.child("gameIds").child(gameId)
            gameIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    callback(dataSnapshot.exists())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
        }
    }
