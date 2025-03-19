package com.example.kotlincoroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val names = listOf(
        "Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Hannah",
        "Ian", "Jack", "Katie", "Liam", "Mia", "Nathan", "Olivia", "Paul", "Quinn",
        "Rachel", "Sam", "Tina", "Umar", "Violet", "William", "Xander", "Yara",
        "Zane", "Sophia", "Ethan", "Daniel", "Isabella"
    )

    val sharedFlow = MutableSharedFlow<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val coroutineScope = rememberCoroutineScope()
            var text by remember { mutableStateOf("") }
            var matchedNames by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    sharedFlow.collectLatest { name ->
                        val isName = names.filter { it.contains(name, ignoreCase = true) }
                        matchedNames = isName.joinToString("\n")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.typography.headlineSmall.fontSize.value.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                TextField(
                    value = text,
                    onValueChange = { myText ->
                        text = myText
                        coroutineScope.launch { sharedFlow.emit(myText) }
                    }
                )
                Text(text = matchedNames)
            }
        }
    }
}