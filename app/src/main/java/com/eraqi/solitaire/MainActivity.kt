package com.eraqi.solitaire


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eraqi.solitaire.ui.theme.SolitaireTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SolitaireTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // ... inside your MainActivity
                    val bridge = remember { NativeBridge() }
                    val message = remember { bridge.getEngineMessage() }
                    val deck = remember { bridge.getDeck() }
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = message)
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        Text(
                            modifier = Modifier.padding(innerPadding),
                            text = "First card rank: ${deck.firstOrNull()?.rank ?: "Empty"}"
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SolitaireTheme {
        Greeting("Android")
    }
}