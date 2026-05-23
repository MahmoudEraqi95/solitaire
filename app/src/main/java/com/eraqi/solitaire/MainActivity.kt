package com.eraqi.solitaire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                    val deck = remember { mutableStateOf(bridge.getDeck()) }
                    val gameState = remember { mutableStateOf(bridge.getGameState()) }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                bridge.shuffleDeck() // Which we'll rename to dealNewGame in C++
                                gameState.value = bridge.getGameState()
                                deck.value = bridge.getDeck()
                            }) {
                                Text("New Game")
                            }
                        }

                        // Tableau View
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            gameState.value.tableau.forEach { column ->
                                Column(Modifier.weight(1f)) {
                                    column.forEach { card ->
                                        CardRow(card)
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun CardRow(card: Card) {
    // Determine card color based on suit (0=Hearts, 1=Diamonds are red)
    val contentColor = if (card.suit == 0 || card.suit == 1) Color.Red else Color.Black

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp) // Fixed height for stacking
            .padding(vertical = 2.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = if (card.isFaceUp) Color.White else Color.Blue // Blue for back of card
        )
    ) {
        if (card.isFaceUp) {
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = getRankString(card.rank),
                    style = MaterialTheme.typography.labelSmall,
                    color = contentColor
                )
                Text(
                    text = getSuitIcon(card.suit),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        } else {
            // Face down state - empty or patterned
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

// Helper functions for better readability
fun getRankString(rank: Int): String = when (rank) {
    1 -> "A"
    11 -> "J"
    12 -> "Q"
    13 -> "K"
    else -> rank.toString()
}

fun getSuitIcon(suit: Int): String = when (suit) {
    0 -> "❤️"
    1 -> "♦️"
    2 -> "♣️"
    else -> "♠️"
}