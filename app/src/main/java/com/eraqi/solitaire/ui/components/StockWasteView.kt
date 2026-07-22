package com.eraqi.solitaire.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eraqi.solitaire.Card

@Composable
fun StockWasteView(
    stock: List<Card>,
    waste: List<Card>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Stock Pile
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(90.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
        ) {
            if (stock.isNotEmpty()) {
                CardView(card = stock.last().copy(isFaceUp = false))
            }
        }

        // Waste Pile
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(90.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
        ) {
            if (waste.isNotEmpty()) {
                CardView(card = waste.last())
            }
        }
    }
}
