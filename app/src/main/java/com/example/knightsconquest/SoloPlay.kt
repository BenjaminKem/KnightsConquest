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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solo_play)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backButton: Button = findViewById(R.id.backButtonSoloPlayScreen)
        backButton.setOnClickListener {
            val mainScreen = Intent(this, MainScreen::class.java)
            startActivity(mainScreen)
        }
        val panel0_0 = findViewById<Button>(R.id.Panel0_0)
        val panel0_1 = findViewById<Button>(R.id.Panel0_1)
        val panel0_2 = findViewById<Button>(R.id.Panel0_2)
        val panel0_3 = findViewById<Button>(R.id.Panel0_3)
        val panel0_4 = findViewById<Button>(R.id.Panel0_4)
        val panel1_0 = findViewById<Button>(R.id.Panel1_0)
        val panel1_1 = findViewById<Button>(R.id.Panel1_1)
        val panel1_2 = findViewById<Button>(R.id.Panel1_2)
        val panel1_3 = findViewById<Button>(R.id.Panel1_3)
        val panel1_4 = findViewById<Button>(R.id.Panel1_4)
        val panel2_0 = findViewById<Button>(R.id.Panel2_0)
        val panel2_1 = findViewById<Button>(R.id.Panel2_1)
        val panel2_2 = findViewById<Button>(R.id.Panel2_2)
        val panel2_3 = findViewById<Button>(R.id.Panel2_3)
        val panel2_4 = findViewById<Button>(R.id.Panel2_4)
        val panel3_0 = findViewById<Button>(R.id.Panel3_0)
        val panel3_1 = findViewById<Button>(R.id.Panel3_1)
        val panel3_2 = findViewById<Button>(R.id.Panel3_2)
        val panel3_3 = findViewById<Button>(R.id.Panel3_3)
        val panel3_4 = findViewById<Button>(R.id.Panel3_4)
        val panel4_0 = findViewById<Button>(R.id.Panel4_0)
        val panel4_1 = findViewById<Button>(R.id.Panel4_1)
        val panel4_2 = findViewById<Button>(R.id.Panel4_2)
        val panel4_3 = findViewById<Button>(R.id.Panel4_3)
        val panel4_4 = findViewById<Button>(R.id.Panel4_4)
        val gameBoard = GameBoard()
        gameBoard.init()
        updateGameBoard(gameBoard)
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
}