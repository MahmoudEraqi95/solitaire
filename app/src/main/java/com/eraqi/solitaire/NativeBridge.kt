package com.eraqi.solitaire
class NativeBridge {
    init {
        // This must match the name in CMakeLists.txt
        System.loadLibrary("solitaire")
    }

    external fun getEngineMessage(): String

    external fun getDeck(): List<Card>

    external fun shuffleDeck()
}