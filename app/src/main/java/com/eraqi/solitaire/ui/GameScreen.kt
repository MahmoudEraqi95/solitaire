package com.eraqi.solitaire.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eraqi.solitaire.GameState
import com.eraqi.solitaire.ui.components.FoundationsView
import com.eraqi.solitaire.ui.components.StockWasteView
import com.eraqi.solitaire.ui.components.TableauView

@Composable
fun GameScreen(
    gameState: GameState,
    onNewGame: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Top Row: Stock, Waste, and Foundations
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StockWasteView(stock = gameState.stock, waste = gameState.waste)
            FoundationsView(foundations = gameState.foundations)
        }

        // Main Area: Tableau
        TableauView(
            tableau = gameState.tableau,
            modifier = Modifier.weight(1f)
        )

        // Bottom Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onNewGame) {
                Text("New Game")
            }
        }
    }
}
