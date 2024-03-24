package com.example.knightsconquest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SoloPlay : AppCompatActivity() {
    val gameBoard = GameBoard()
    var selectedFigure: Array<Int>? = null
    var selectedCard : String? = null
    var selectedField: Array<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solo_play)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gameBoard.init()
        updateGameBoard(gameBoard)
        val backButton: Button = findViewById(R.id.backButtonSoloPlayScreen)
        backButton.setOnClickListener {
            val mainScreen = Intent(this, MainScreen::class.java)
            startActivity(mainScreen)
        }
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
    }
    private fun updateGameBoard(gameBoard: GameBoard){
        for (rowCounter in 0..4) {
            for (columnCounter in 0..4) {
                if(gameBoard.getPieceAt(rowCounter,columnCounter).figure == FigureType.KNIGHT && gameBoard.getPieceAt(rowCounter,columnCounter).color == TileColor.RED){
                    val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground = ContextCompat.getDrawable(this,R.drawable.redknightpanel)
                }else if(gameBoard.getPieceAt(rowCounter,columnCounter).figure == FigureType.KNIGHT && gameBoard.getPieceAt(rowCounter,columnCounter).color == TileColor.BLUE){
                    val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground = ContextCompat.getDrawable(this,R.drawable.blueknightpanel)
                }else if(gameBoard.getPieceAt(rowCounter,columnCounter).figure == FigureType.KING && gameBoard.getPieceAt(rowCounter,columnCounter).color == TileColor.BLUE){
                    val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground = ContextCompat.getDrawable(this,R.drawable.bluekingpanel)
                }else if(gameBoard.getPieceAt(rowCounter,columnCounter).figure == FigureType.KING && gameBoard.getPieceAt(rowCounter,columnCounter).color == TileColor.RED){
                    val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground = ContextCompat.getDrawable(this,R.drawable.redkingpanel)
                }else{
                    val panelId = resources.getIdentifier("Panel$rowCounter" + "_$columnCounter", "id", packageName)
                    val panelToChange = findViewById<Button>(panelId)
                    panelToChange.foreground = ContextCompat.getDrawable(this,R.drawable.panel)
                }
            }
        }
    }
    // Eine Funktion, die das Klicken auf die Panels handhabt
    fun setPanelClickListener(panel: Button, row: Int, col: Int) {
        panel.setOnClickListener {
            if (gameBoard.getPieceAt(row, col).figure == FigureType.NONE) {
                selectedField = arrayOf(row, col)
            } else {
                selectedFigure = arrayOf(row, col)
            }
        }
    }
}