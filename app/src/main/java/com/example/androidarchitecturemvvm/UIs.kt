package com.example.androidarchitecturemvvm

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidarchitecturemvvm.AllProducts.AllProductsActivity
import com.example.androidarchitecturemvvm.Data.model.Products
import com.example.androidarchitecturemvvm.Favourites.view.FavouritesActivity

@Composable
fun mainScreen(){
    val context = LocalContext.current
    Column (modifier = Modifier.padding(15.dp)){
        Button(onClick = {
            val intent = Intent(context, AllProductsActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Home")
        } //navigate to home


        Button(onClick = {
            val intent = Intent(context, FavouritesActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Favourites")
        } //navigate to favourites
    }
}






@Composable
fun productsScreen(products_list: List<Products>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products_list) { product ->
            ProductsRow(product)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductsRow(product: Products) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            model = product.thumbnail,
            contentDescription = "Product Image",
            modifier = Modifier.padding(15.dp)
        )
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "⭐ ${product.rating}")
            Text(text = product.brand ?: "Unknown Brand")
            Text(text = product.title ?: "No Title Available")
            Button(onClick = {}) { //add to favourites
                Row(modifier = Modifier.padding(15.dp), horizontalArrangement = Arrangement.spacedBy(15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.favorite),
                        contentDescription = null,
                    )
                    Text(text = "add to favourites")
                }
            }
        }
    }
}




