package com.example.androidarchitecturemvvm

import android.content.Intent
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidarchitecturemvvm.AllProducts.AllProductsActivity
import com.example.androidarchitecturemvvm.Favourites.view.FavouritesActivity

class MainActivity : ComponentActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)

                ) {
                    Button(onClick = { navigateToProducts() }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Home")
                    }
                    Button(onClick = { navigateToFavourites() }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Favourites")
                    }
                    Button(onClick = { finish() }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Exit")
                    }
                }
            }
        }

        private fun navigateToProducts() {
            val intent = Intent(this, AllProductsActivity::class.java)
            startActivity(intent)
        }

        private fun navigateToFavourites() {
            val intent = Intent(this, FavouritesActivity::class.java)
            startActivity(intent)
        }

}