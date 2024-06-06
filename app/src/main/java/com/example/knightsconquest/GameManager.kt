package com.example.knightsconquest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class GameManager {
    var playerOne:PlayerState = PlayerState.WAITING;
    var playerTwo:PlayerState = PlayerState.WAITING;
    var playerTurn:Turn = Turn.BLUE
    var gameID:String = "";
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
}