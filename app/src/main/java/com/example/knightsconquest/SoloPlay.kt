package com.example.knightsconquest

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class SoloPlay : AppCompatActivity() {
    val game = GameController(this)
    var selectedFigure: Array<Int>? = null
    var selectedCard: Card? = null
    var selectedField: Array<Int>? = null
    val cardDrawables = mapOf(
        "boar" to R.drawable.boar_card,
        "cobra" to R.drawable.cobra_card,
        "crab" to R.drawable.crab_card,
        "crane" to R.drawable.crane_card,
        "dragon" to R.drawable.dragon_card,
        "eel" to R.drawable.eel_card,
        "elefant" to R.drawable.elefant_card,
        "frog" to R.drawable.frog_card,
        "goose" to R.drawable.goose_card,
        "horse" to R.drawable.horse_card,
        "mantis" to R.drawable.mantis_card,
        "monkey" to R.drawable.monkey_card,
        "ox" to R.drawable.ox_card,
        "rabbit" to R.drawable.rabbit_card,
        "rooster" to R.drawable.rooster_card,
        "tiger" to R.drawable.tiger_card
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solo_play)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        game.startGame()
        updateGameBoard(game.gameBoard)
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
            if (game.gameBoard.turnIndicator == TileColor.RED) {
                selectedCard = game.getRedCard(0)
                topMidPanel.isSelected = false
                topLeftPanel.isSelected = true
                if(selectedFigure != null){
                    deselectEverything()
                    highlightPossibleMoves()
                }
            }
        }
        topMidPanel.setOnClickListener {
            if (game.gameBoard.turnIndicator == TileColor.RED) {
                selectedCard = game.getRedCard(1)
                topMidPanel.isSelected = true
                topLeftPanel.isSelected = false
                if(selectedFigure != null){
                    deselectEverything()
                    highlightPossibleMoves()
                }
            }
        }
        val bottomLeftPanel = findViewById<Button>(R.id.BottomLeftCard)
        val bottomMidPanel = findViewById<Button>(R.id.BottomMidCard)
        bottomLeftPanel.setOnClickListener {
            if (game.gameBoard.turnIndicator == TileColor.BLUE) {
                selectedCard = game.getBlueCard(0)
                bottomLeftPanel.isSelected = true
                bottomMidPanel.isSelected = false
                if(selectedFigure != null){
                    deselectEverything()
                    highlightPossibleMoves()
                }
            }
        }
        bottomMidPanel.setOnClickListener {
            if (game.gameBoard.turnIndicator == TileColor.BLUE) {
                selectedCard = game.getBlueCard(1)
                bottomLeftPanel.isSelected = false
                bottomMidPanel.isSelected = true
                if(selectedFigure != null){
                    deselectEverything()
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
                var cardName = game.getRedCard(0).name
                var drawableResId = cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                var drawable = ContextCompat.getDrawable(this, drawableResId)
                val topLeftCard = findViewById<Button>(R.id.TopLeftCard)
                topLeftCard.foreground = drawable

                cardName = game.getRedCard(1).name
                drawableResId = cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val topMidCard = findViewById<Button>(R.id.TopMidCard)
                topMidCard.foreground = drawable

                cardName = game.getStackCard().name
                drawableResId = cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val topRightCard = findViewById<Button>(R.id.TopRightCard)
                topRightCard.foreground = drawable

                cardName = game.getBlueCard(0).name
                drawableResId = cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val bottomLeftCard = findViewById<Button>(R.id.BottomLeftCard)
                bottomLeftCard.foreground = drawable

                cardName = game.getBlueCard(1).name
                drawableResId = cardDrawables[cardName]
                    ?: R.drawable.tabpanellight // Hier wird ein Standard-Drawable verwendet, falls die Karte nicht in der Map gefunden wird
                drawable = ContextCompat.getDrawable(this, drawableResId)
                val bottomMidCard = findViewById<Button>(R.id.BottomMidCard)
                bottomMidCard.foreground = drawable

                cardName = game.getStackCard().name
                drawableResId = cardDrawables[cardName]
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
            if (game.gameBoard.getPieceAt(row, col).figure == FigureType.NONE || game.gameBoard.getPieceAt(row, col).color != game.gameBoard.turnIndicator) {
                if (selectedFigure == null || selectedCard == null) {
                    selectedFigure = null
                    selectedCard = null
                    deselectEverything()
                }
                selectedField = arrayOf(row, col)
                if (selectedFigure != null && selectedCard != null) {
                    if (game.makeMove(selectedCard!!, selectedFigure!!, selectedField!!)) {
                        selectedField = arrayOf(row, col)
                        updateGameBoard(game.gameBoard)
                        if (game.gameBoard.didSomeoneWin()) {
                            val HowToPlay = Intent(this, HowToPlay::class.java)
                            startActivity(HowToPlay)
                        }
                        selectedFigure = null
                        selectedCard = null
                        selectedField = null
                        changePlayTurn()
                    } else {
                        selectedFigure = null
                        selectedCard = null
                        selectedField = null
                        deselectEverything()
                    }
                }
            } else {
                selectedFigure = arrayOf(row, col)
                panel.isSelected = true
                if(selectedCard != null){
                    deselectEverything()
                    highlightPossibleMoves()
                }
            }
        }
    }

    private fun changePlayTurn() {
        val playerturnblue = findViewById<ImageView>(R.id.playerturnblue)
        val playerturnred = findViewById<ImageView>(R.id.playerturnred)
        if(game.gameBoard.turnIndicator == TileColor.RED){
            playerturnblue.isVisible = false
            playerturnred.isVisible = true
        }else{
            playerturnred.isVisible = false
            playerturnblue.isVisible = true
        }
    }

    private fun highlightPossibleMoves() {
        for (moves in selectedCard!!.movements){
            var toXCord:Int? = null
            var toYCord:Int? = null
            if(game.gameBoard.turnIndicator == TileColor.RED){
                toXCord = selectedFigure!![0] + moves[0]*-1
                toYCord = selectedFigure!![1] + moves[1]*-1
            }else{
                toXCord = selectedFigure!![0] + moves[0]
                toYCord = selectedFigure!![1] + moves[1]
            }
            if(game.gameBoard.isValidMove(selectedCard!!,selectedFigure!![0],selectedFigure!![1],toXCord,toYCord)){
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
}