package com.eraqi.solitaire.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eraqi.solitaire.Card

@Composable
fun TableauView(
    tableau: List<List<Card>>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tableau.forEach { column ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                column.forEachIndexed { index, card ->
                    CardView(
                        card = card,
                        modifier = Modifier
                            .offset(y = (index * 20).dp) // Vertical offset for overlapping
                    )
                }
            }
        }
    }
}
