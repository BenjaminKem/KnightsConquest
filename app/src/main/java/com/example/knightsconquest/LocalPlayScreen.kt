package com.example.knightsconquest

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class LocalPlayScreen : AppCompatActivity() {
    private var isMusicEnabled = false
    private var isMusicBound = false
    private var musicPaused = false
    val game = GameController()
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
        "tiger" to R.drawable.tiger_card,
        "bear" to R.drawable.bear_card,
        "cat" to R.drawable.cat_card,
        "centipede" to R.drawable.centipede_card,
        "dog" to R.drawable.dog_card,
        "fox" to R.drawable.fox_card,
        "giraffe" to R.drawable.giraffe_card,
        "goat" to R.drawable.goat_card,
        "hornet" to R.drawable.hornet_card,
        "iguna" to R.drawable.iguna_card,
        "kirin" to R.drawable.kirin_card,
        "lobster" to R.drawable.lobster_card,
        "mouse" to R.drawable.mouse_card,
        "otter" to R.drawable.otter_card,
        "panda" to R.drawable.panda_card,
        "phoenix" to R.drawable.phoenix_card,
        "rat" to R.drawable.rat_card,
        "sable" to R.drawable.sable_card,
        "seasnake" to R.drawable.seasnake_card,
        "serow" to R.drawable.serow_card,
        "sheep" to R.drawable.sheep_card,
        "steer" to R.drawable.steer_card,
        "tanuki" to R.drawable.tanuki_card,
        "turtle" to R.drawable.turtle_card,
        "viper" to R.drawable.viper_card,
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
        isMusicEnabled = intent.getBooleanExtra("isMusicEnabled", true)
        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)
        musicSwitch.isChecked = isMusicEnabled
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            isMusicEnabled = isChecked
            val songResId = R.raw.battlemusic
            val musicIntent = Intent(this, MusicService::class.java).apply {
                putExtra("songResId", songResId)
            }
            if (isMusicEnabled) {
                startService(musicIntent)
            } else {
                stopService(musicIntent)
            }
        }

        if (isMusicEnabled) {
            val musicIntent = Intent(this, MusicService::class.java).apply {
                putExtra("songResId", R.raw.battlemusic)
            }
            if (!isServiceRunning(MusicService::class.java)) {
                startService(musicIntent)
            } else {
                startService(musicIntent)
            }
        }
        game.startGame()
        updateGameBoard(game.gameBoard)
        val backButton: Button = findViewById(R.id.backButtonSoloPlayScreen)
        backButton.setOnClickListener {
            val mainScreen = Intent(this, MainScreen::class.java)
            mainScreen.putExtra("isMusicEnabled", isMusicEnabled)
            startActivity(mainScreen)
        }
        val playerturnred = findViewById<ImageView>(R.id.playerturnred)
        playerturnred.isVisible = false
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
                    deselectHighlightedFields()
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
                    deselectHighlightedFields()
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
                    deselectHighlightedFields()
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
                    if(rowCounter == 4 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_red_knight_blue_castle)
                    }else if(rowCounter == 0 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_red_knight_red_castle)
                    }else{
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.redknightpanel)
                    }
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
                    if(rowCounter == 4 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_blue_knight_castle)
                    }else if(rowCounter == 0 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_blue_knight_red_castle)
                    }else{
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.blueknightpanel)
                    }
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
                    if(rowCounter == 4 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_blue_king_blue_castle)
                    }else if(rowCounter == 0 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_blue_king_red_castle)
                    }else{
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.bluekingpanel)
                    }
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
                    if(rowCounter == 0 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_red_king_red_castle)
                    }else if(rowCounter == 4 && columnCounter == 2){
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_red_king_blue_castle)
                    }else{
                        val panelToChange = findViewById<Button>(panelId)
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.redkingpanel)
                    }
                } else {
                    val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                    val panelToChange = findViewById<Button>(panelId)
                    if(rowCounter == 0 && columnCounter == 2){
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_red_castle)
                    }else if(rowCounter == 4 && columnCounter == 2){
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel_blue_castle)
                    }else{
                        panelToChange.foreground = ContextCompat.getDrawable(this, R.drawable.panel)

                    }
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
                            if(game.gameBoard.redWon){
                                val endGameScreen = Intent(this, RedWinScreen::class.java)
                                endGameScreen.putExtra("isMusicEnabled", isMusicEnabled)
                                startActivity(endGameScreen)
                            }else{
                                val endGameScreen = Intent(this, BlueWinScreen::class.java)
                                endGameScreen.putExtra("isMusicEnabled", isMusicEnabled)
                                startActivity(endGameScreen)
                            }
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
                if(selectedCard != null){
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
                if(game.gameBoard.getPieceAt(rowCounter,columnCounter).figure != FigureType.NONE){
                    panelToChange.isSelected = false;
                    panelToChange.isActivated = false
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
    override fun onStop() {
        super.onStop()
        if (isMusicEnabled) {
            pauseMusic()
        }
    }
    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!isMusicEnabled) {
            stopService(Intent(this, MusicService::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainScreen", "onResume called")
        if (musicPaused && isMusicEnabled) {
            resumeMusic()
        }
    }

    private fun pauseMusic() {
        val musicServiceIntent = Intent(this, MusicService::class.java).apply {
            putExtra("action", "pause")
        }
        startService(musicServiceIntent)
        musicPaused = true
    }

    private fun resumeMusic() {
        val musicServiceIntent = Intent(this, MusicService::class.java).apply {
            putExtra("action", "resume")
        }
        startService(musicServiceIntent)
        musicPaused = false
    }
}