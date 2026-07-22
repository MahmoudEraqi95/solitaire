package com.eraqi.solitaire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.eraqi.solitaire.ui.GameScreen
import com.eraqi.solitaire.ui.theme.SolitaireTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bridge = NativeBridge()

        setContent {
            SolitaireTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val gameState = remember { mutableStateOf(bridge.getGameState()) }

                    GameScreen(
                        gameState = gameState.value,
                        onNewGame = {
                            bridge.shuffleDeck()
                            gameState.value = bridge.getGameState()
                        }
                    )
                }
            }
        }
    }
}
