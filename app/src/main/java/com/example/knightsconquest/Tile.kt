package com.example.knightsconquest
import com.example.knightsconquest.FigureType
import com.example.knightsconquest.TileColor
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
class Tile (
    var color: TileColor? = null,
    var figure: FigureType? = null
)