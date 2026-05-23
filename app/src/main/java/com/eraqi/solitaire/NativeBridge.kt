package com.eraqi.solitaire
class NativeBridge {
    init {
        System.loadLibrary("solitaire")
    }

    external fun getDeck(): List<Card>

    external fun shuffleDeck()

    external fun getGameState(): GameState
}