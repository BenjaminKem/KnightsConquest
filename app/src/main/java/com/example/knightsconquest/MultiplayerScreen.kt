package com.example.knightsconquest


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MultiplayerScreen : AppCompatActivity() {
    val db = FirebaseDatabase.getInstance()
    var gameManager: GameManager = GameManager();
    var player:String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_multiplayer_play)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val gameId = intent.getStringExtra("gameId")
        if (gameId != null) {
            getGameValue(gameId, onSuccess = { gameValue ->
                // Erfolgreiches Abrufen der Spieldaten
                val objectMapper = ObjectMapper()
                val gameManager: GameManager = objectMapper.readValue(gameValue)
                this.gameManager = gameManager;
                println("Spiel wurde aus Datenbank wiederhergestellt}")
            }, onFailure = { exception ->
                // Fehler beim Abrufen der Spieldaten
                println("Fehler: ${exception.message}")
            })
        }
        player = intent.getStringExtra(player).toString()
        gameManager.game.startGame()
        updateGameBoard(gameManager.game.gameBoard)
        val backButton: Button = findViewById(R.id.backButtonSoloPlayScreen)
        backButton.setOnClickListener {
            val mainScreen = Intent(this, MainScreen::class.java)
            startActivity(mainScreen)
        }
        val playerturnblue = findViewById<ImageView>(R.id.playerturnblue)
        playerturnblue.isVisible = false
        // Panels initialisieren und Click Listener setzen
        val panel0_0 = findViewById<Button>(R.id.Panel0_0)
        setPanelClickListener(panel0_0, 0, 0)

        val panel0_1 = findViewById<Button>(R.id.Panel0_1)
        setPanelClickListener(panel0_1, 0, 1)

        val panel0_2 = findViewById<Button>(R.id.Panel0_2)
        setPanelClickListener(panel0_2, 0, 2)

        val panel0_3 = findViewById<Button>(R.id.Panel0_3)
        setPanelClickListener(panel0_3, 0, 3)

        val panel0_4 = findViewById<Button>(R.id.Panel0_4)
        setPanelClickListener(panel0_4, 0, 4)

        val panel1_0 = findViewById<Button>(R.id.Panel1_0)
        setPanelClickListener(panel1_0, 1, 0)

        val panel1_1 = findViewById<Button>(R.id.Panel1_1)
        setPanelClickListener(panel1_1, 1, 1)

        val panel1_2 = findViewById<Button>(R.id.Panel1_2)
        setPanelClickListener(panel1_2, 1, 2)

        val panel1_3 = findViewById<Button>(R.id.Panel1_3)
        setPanelClickListener(panel1_3, 1, 3)

        val panel1_4 = findViewById<Button>(R.id.Panel1_4)
        setPanelClickListener(panel1_4, 1, 4)

        val panel2_0 = findViewById<Button>(R.id.Panel2_0)
        setPanelClickListener(panel2_0, 2, 0)

        val panel2_1 = findViewById<Button>(R.id.Panel2_1)
        setPanelClickListener(panel2_1, 2, 1)

        val panel2_2 = findViewById<Button>(R.id.Panel2_2)
        setPanelClickListener(panel2_2, 2, 2)

        val panel2_3 = findViewById<Button>(R.id.Panel2_3)
        setPanelClickListener(panel2_3, 2, 3)

        val panel2_4 = findViewById<Button>(R.id.Panel2_4)
        setPanelClickListener(panel2_4, 2, 4)

        val panel3_0 = findViewById<Button>(R.id.Panel3_0)
        setPanelClickListener(panel3_0, 3, 0)

        val panel3_1 = findViewById<Button>(R.id.Panel3_1)
        setPanelClickListener(panel3_1, 3, 1)

        val panel3_2 = findViewById<Button>(R.id.Panel3_2)
        setPanelClickListener(panel3_2, 3, 2)

        val panel3_3 = findViewById<Button>(R.id.Panel3_3)
        setPanelClickListener(panel3_3, 3, 3)

        val panel3_4 = findViewById<Button>(R.id.Panel3_4)
        setPanelClickListener(panel3_4, 3, 4)

        val panel4_0 = findViewById<Button>(R.id.Panel4_0)
        setPanelClickListener(panel4_0, 4, 0)

        val panel4_1 = findViewById<Button>(R.id.Panel4_1)
        setPanelClickListener(panel4_1, 4, 1)

        val panel4_2 = findViewById<Button>(R.id.Panel4_2)
        setPanelClickListener(panel4_2, 4, 2)

        val panel4_3 = findViewById<Button>(R.id.Panel4_3)
        setPanelClickListener(panel4_3, 4, 3)

        val panel4_4 = findViewById<Button>(R.id.Panel4_4)
        setPanelClickListener(panel4_4, 4, 4)

        val topLeftPanel = findViewById<Button>(R.id.TopLeftCard)
        val topMidPanel = findViewById<Button>(R.id.TopMidCard)
        topLeftPanel.setOnClickListener {
            if (gameManager.game.gameBoard.turnIndicator == TileColor.RED) {
                gameManager.selectedCard = gameManager.game.getRedCard(0)
                topMidPanel.isSelected = false
                topLeftPanel.isSelected = true
                if(gameManager.selectedFigure != null){
                    deselectHighlightedFields()
                    highlightPossibleMoves()
                }
            }
        }
        topMidPanel.setOnClickListener {
            if (gameManager.game.gameBoard.turnIndicator == TileColor.RED) {
                gameManager.selectedCard = gameManager.game.getRedCard(1)
                topMidPanel.isSelected = true
                topLeftPanel.isSelected = false
                if(gameManager.selectedFigure != null){
                    deselectHighlightedFields()
                    highlightPossibleMoves()
                }
            }
        }
        val bottomLeftPanel = findViewById<Button>(R.id.BottomLeftCard)
        val bottomMidPanel = findViewById<Button>(R.id.BottomMidCard)
        bottomLeftPanel.setOnClickListener {
            if (gameManager.game.gameBoard.turnIndicator == TileColor.BLUE) {
                gameManager.selectedCard = gameManager.game.getBlueCard(0)
                bottomLeftPanel.isSelected = true
                bottomMidPanel.isSelected = false
                if(gameManager.selectedFigure != null){
                    deselectHighlightedFields()
                    highlightPossibleMoves()
                }
            }
        }
        bottomMidPanel.setOnClickListener {
            if (gameManager.game.gameBoard.turnIndicator == TileColor.BLUE) {
                gameManager.selectedCard = gameManager.game.getBlueCard(1)
                bottomLeftPanel.isSelected = false
                bottomMidPanel.isSelected = true
                if(gameManager.selectedFigure != null){
                    deselectHighlightedFields()
                    highlightPossibleMoves()
                }
            }
        }
    }

    private fun updateGameBoard(gameBoard: GameBoard) {
        for (rowCounter in 0..4) {
            for (columnCounter in 0..4) {
                if (gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).figure == FigureType.KNIGHT && gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).color == TileColor.RED
                ) {
                    val panelId = resources.getIdentifier(
                        "Panel$rowCounter" + "_$columnCounter",
                        "id",
                        packageName
                    )
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground =
                        ContextCompat.getDrawable(this, R.drawable.redknightpanel)
                } else if (gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).figure == FigureType.KNIGHT && gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).color == TileColor.BLUE
                ) {
                    val panelId = resources.getIdentifier(
                        "Panel$rowCounter" + "_$columnCounter",
                        "id",
                        packageName
                    )
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground =
                        ContextCompat.getDrawable(this, R.drawable.blueknightpanel)
                } else if (gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).figure == FigureType.KING && gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).color == TileColor.BLUE
                ) {
                    val panelId = resources.getIdentifier(
                        "Panel$rowCounter" + "_$columnCounter",
                        "id",
                        packageName
                    )
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground =
                        ContextCompat.getDrawable(this, R.drawable.bluekingpanel)
                } else if (gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).figure == FigureType.KING && gameBoard.getPieceAt(
                        rowCounter,
                        columnCounter
                    ).color == TileColor.RED
                ) {
                    val panelId = resources.getIdentifier(
                        "Panel$rowCounter" + "_$columnCounter",
                        "id",
                        packageName
                    )
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground =
                        ContextCompat.getDrawable(this, R.drawable.redkingpanel)
                } else {
                    val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel)
                }
                var cardName = gameManager.game.getRedCard(0).name
                var drawableResId = gameManager.cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                var drawable = ContextCompat.getDrawable(this, drawableResId)
                val topLeftCard = findViewById<Button>(R.id.TopLeftCard)
                topLeftCard.foreground = drawable

                cardName = gameManager.game.getRedCard(1).name
                drawableResId = gameManager.cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val topMidCard = findViewById<Button>(R.id.TopMidCard)
                topMidCard.foreground = drawable

                cardName = gameManager.game.getStackCard().name
                drawableResId = gameManager.cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val topRightCard = findViewById<Button>(R.id.TopRightCard)
                topRightCard.foreground = drawable

                cardName = gameManager.game.getBlueCard(0).name
                drawableResId = gameManager.cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val bottomLeftCard = findViewById<Button>(R.id.BottomLeftCard)
                bottomLeftCard.foreground = drawable

                cardName = gameManager.game.getBlueCard(1).name
                drawableResId = gameManager.cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val bottomMidCard = findViewById<Button>(R.id.BottomMidCard)
                bottomMidCard.foreground = drawable

                cardName = gameManager.game.getStackCard().name
                drawableResId = gameManager.cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val bottomRightCard = findViewById<Button>(R.id.BottomRightCard)
                bottomRightCard.foreground = drawable
            }
        }
        deselectEverything()
    }

    // Eine Funktion, die das Klicken auf die Panels handhabt
    fun setPanelClickListener(panel: Button, row: Int, col: Int) {
        panel.setOnClickListener {
            if (gameManager.game.gameBoard.getPieceAt(row, col).figure == FigureType.NONE || gameManager.game.gameBoard.getPieceAt(row, col).color != gameManager.game.gameBoard.turnIndicator) {
                if (gameManager.selectedFigure == null || gameManager.selectedCard == null) {
                    gameManager.selectedFigure = null
                    gameManager.selectedCard = null
                    deselectEverything()
                }
                gameManager.selectedField = arrayOf(row, col)
                if (gameManager.selectedFigure != null && gameManager.selectedCard != null) {
                    if (gameManager.game.makeMove(gameManager.selectedCard!!, gameManager.selectedFigure!!, gameManager.selectedField!!)) {
                        gameManager.selectedField = arrayOf(row, col)
                        updateGameBoard(gameManager.game.gameBoard)
                        if (gameManager.game.gameBoard.didSomeoneWin()) {
                            val HowToPlay = Intent(this, HowToPlay::class.java)
                            startActivity(HowToPlay)
                        }

                        gameManager.selectedFigure = null
                        gameManager.selectedCard = null
                        gameManager.selectedField = null
                        changePlayTurn()
                    } else {
                        gameManager.selectedFigure = null
                        gameManager.selectedCard = null
                        gameManager.selectedField = null
                        deselectEverything()
                    }
                }
            } else {
                gameManager.selectedFigure = arrayOf(row, col)
                if(gameManager.selectedCard != null){
                    deselectHighlightedFields()
                    deselectFigures()
                    panel.isSelected = true
                    highlightPossibleMoves()
                }
            }
        }
    }

    private fun deselectFigures() {
        for (rowCounter in 0..4) {
            for (columnCounter in 0..4) {
                val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                val panelToChange = findViewById<Button>(panelId)
                if(gameManager.game.gameBoard.getPieceAt(rowCounter,columnCounter).figure != FigureType.NONE){
                    panelToChange.isSelected = false;
                    panelToChange.isActivated = false
                }

            }
        }
    }

    private fun changePlayTurn() {
        val playerturnblue = findViewById<ImageView>(R.id.playerturnblue)
        val playerturnred = findViewById<ImageView>(R.id.playerturnred)
        if(gameManager.game.gameBoard.turnIndicator == TileColor.RED){
            playerturnblue.isVisible = false
            playerturnred.isVisible = true
        }else{
            playerturnred.isVisible = false
            playerturnblue.isVisible = true
        }
    }

    private fun highlightPossibleMoves() {
        for (moves in gameManager.selectedCard!!.movements){
            var toXCord:Int? = null
            var toYCord:Int? = null
            if(gameManager.game.gameBoard.turnIndicator == TileColor.RED){
                toXCord = gameManager.selectedFigure!![0] + moves[0]*-1
                toYCord = gameManager.selectedFigure!![1] + moves[1]*-1
            }else{
                toXCord = gameManager.selectedFigure!![0] + moves[0]
                toYCord = gameManager.selectedFigure!![1] + moves[1]
            }
            if(gameManager.game.gameBoard.isValidMove(gameManager.selectedCard!!,gameManager.selectedFigure!![0],gameManager.selectedFigure!![1],toXCord,toYCord)){
                val panelId = resources.getIdentifier("Panel$toXCord" + "_$toYCord", "id", packageName)
                val panelToHighlight = findViewById<Button>(panelId)
                panelToHighlight.isActivated = true
            }
        }
    }

    fun deselectEverything() {
        for (rowCounter in 0..4) {
            for (columnCounter in 0..4) {
                val panelId = resources.getIdentifier(
                    "Panel$rowCounter" + "_$columnCounter",
                    "id",
                    packageName
                )
                val panelToChange = findViewById<Button>(panelId)
                panelToChange.isSelected = false;
                panelToChange.isActivated = false
            }
        }
        val topLeftPanel = findViewById<Button>(R.id.TopLeftCard)
        val topMidPanel = findViewById<Button>(R.id.TopMidCard)
        val bottomLeftPanel = findViewById<Button>(R.id.BottomLeftCard)
        val bottomMidPanel = findViewById<Button>(R.id.BottomMidCard)
        topLeftPanel.isSelected = false
        topMidPanel.isSelected = false
        bottomLeftPanel.isSelected = false
        bottomMidPanel.isSelected = false
    }
    fun deselectHighlightedFields(){
        for (rowCounter in 0..4) {
            for (columnCounter in 0..4) {
                val panelId = resources.getIdentifier(
                    "Panel$rowCounter" + "_$columnCounter",
                    "id",
                    packageName
                )
                val panelToChange = findViewById<Button>(panelId)
                panelToChange.isActivated = false
            }
        }
    }
        private fun writeGameToDatabase(gameId: String,game: String) {
            var databaseReference = db.reference.child("gameIds").child(gameId)
            databaseReference.setValue(game).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Spiel Daten erfolgreich in die Datenbank geschrieben.")
                } else {
                    println("Fehler beim Schreiben der Spiel-ID in die Datenbank: ${task.exception?.message}")
                }
            }
        }
        private fun addGameChangeListener(gameId: String) {
            val databaseReference = db.reference.child("gameIds").child(gameId)
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Prüfen, ob die Daten vorhanden sind
                    if (snapshot.exists()) {
                        val updatedGame = snapshot.getValue(String::class.java)
                        val objectMapper = ObjectMapper()
                        println("Spiel Daten wurden aktualisiert: $updatedGame")
                        val gameManager: GameManager = objectMapper.readValue(updatedGame.toString())
                        if(gameManager.playerTwo == PlayerState.JOINTED){
                            handleUpdatedGame(gameId)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    println("Fehler beim Abhören von Spiel-Änderungen: ${error.message}")
                }
            })
        }
        private fun handleUpdatedGame(gameId: String) {
            val multiplayerPlayScreenScreen = Intent(this, MultiplayerScreen::class.java)
            multiplayerPlayScreenScreen.putExtra("gameId",gameId)
            multiplayerPlayScreenScreen.putExtra("player","first")
            startActivity(multiplayerPlayScreenScreen)
        }
    fun getGameValue(gameId: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("gameIds").child(gameId)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    try {
                        val gameValue = snapshot.getValue(String::class.java)
                        if (gameValue != null) {
                            onSuccess(gameValue)
                        } else {
                            onFailure(Exception("Game value is null"))
                        }
                    } catch (e: Exception) {
                        onFailure(e)
                    }
                } else {
                    onFailure(Exception("Game not found"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
