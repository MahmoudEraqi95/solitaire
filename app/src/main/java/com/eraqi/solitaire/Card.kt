package com.eraqi.solitaire

// This must match the structure of the Card struct in game_engine.hpp
data class Card(
    val rank: Int,
    val suit: Int,
    val isFaceUp: Boolean
)