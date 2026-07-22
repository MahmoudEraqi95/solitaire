package com.eraqi.solitaire.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eraqi.solitaire.Card

@Composable
fun CardView(
    card: Card,
    modifier: Modifier = Modifier
) {
    val contentColor = if (card.suit == 0 || card.suit == 1) Color.Red else Color.Black

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp), // Increased height for better visibility
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = if (card.isFaceUp) Color.White else Color.Blue
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
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

private fun getRankString(rank: Int): String = when (rank) {
    1 -> "A"
    11 -> "J"
    12 -> "Q"
    13 -> "K"
    else -> rank.toString()
}

private fun getSuitIcon(suit: Int): String = when (suit) {
    0 -> "❤️"
    1 -> "♦️"
    2 -> "♣️"
    else -> "♠️"
}
