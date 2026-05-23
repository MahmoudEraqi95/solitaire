package com.eraqi.solitaire


data class GameState(
    val stock: List<Card>,
    val waste: List<Card>,
    val foundations: List<List<Card>>, // 4 lists
    val tableau: List<List<Card>>      // 7 lists
)