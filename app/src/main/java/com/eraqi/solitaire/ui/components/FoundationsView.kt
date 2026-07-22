package com.eraqi.solitaire.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eraqi.solitaire.Card

@Composable
fun FoundationsView(
    foundations: List<List<Card>>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(4) { i ->
            val pile = foundations.getOrNull(i) ?: emptyList()
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(90.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (pile.isEmpty()) {
                    Text("A", color = Color.LightGray)
                } else {
                    CardView(card = pile.last())
                }
            }
        }
    }
}
