package com.eraqi.solitaire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eraqi.solitaire.ui.theme.SolitaireTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bridge = NativeBridge()

        setContent {
            SolitaireTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val deck = remember { mutableStateOf( bridge.getDeck()) }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Button(onClick = {
                            bridge.shuffleDeck()
                            deck.value = bridge.getDeck()
                        }) {
                            Text("Shuffle")
                        }
                        Text(
                            text = "Deck from C++ Engine (${deck.value.size} cards)",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn {
                            items(deck.value) { card ->
                                CardRow(card)
                                HorizontalDivider()
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Rank: ${card.rank}")
        Text(text = "Suit: ${when(card.suit) {
            0 -> "Hearts ❤️"
            1 -> "Diamonds ♦️"
            2 -> "Clubs ♣️"
            else -> "Spades ♠️"
        }}")
    }
}