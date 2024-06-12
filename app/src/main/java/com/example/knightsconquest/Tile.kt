package com.example.knightsconquest
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
//TODO: data keyword vor class entfernen falls projekt kracht
data class Tile (
    var color: TileColor? = null,
    var figure: FigureType? = null
)