package com.example.androidarchitecturemvvm.Favourites.view

import ProductsRemoteDataSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidarchitecturemvvm.Data.Repo.ProductsRepo
import com.example.androidarchitecturemvvm.Data.local.ProductsLocalDataSource
import com.example.androidarchitecturemvvm.Data.local.RoomHelper
import com.example.androidarchitecturemvvm.Data.model.Products
import com.example.androidarchitecturemvvm.Data.remote.RetrofitHelper
import com.example.androidarchitecturemvvm.Favourites.viewModel.FavoritesViewModel
import com.example.androidarchitecturemvvm.Favourites.viewModel.FavouritesViewModelFactory
import kotlinx.coroutines.launch

class FavouritesActivity : ComponentActivity() {

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val localDataSource = ProductsLocalDataSource(RoomHelper.getInstance(this).getProductsDao())
        val remoteDataSource = ProductsRemoteDataSource(RetrofitHelper.retrofitService)
        val repo = ProductsRepo.getInstance(remoteDataSource, localDataSource)

        viewModel = ViewModelProvider(this, FavouritesViewModelFactory(repo)).get(FavoritesViewModel::class.java)

        setContent {
            val products by viewModel.Products.collectAsState()
            val message by viewModel.message.collectAsState()

            Column(modifier = Modifier.padding(16.dp)) {
                if (products.isNullOrEmpty()) {
                    Text(text = "No products available.", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn {
                        items(products!!) { product ->
                            ProductsRow(product = product, action = { viewModel.deleteProduct(product) })
                        }
                    }
                }

                message?.let {
                    Text(text = it, modifier = Modifier.padding(16.dp))
                }
            }
        }

        viewModel.fetchFavourites()
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun ProductsRow(product: Products, action: (Products) -> Unit) {
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
                Button(onClick = { viewModel.deleteProduct(product) }) {
                    Text(text = "Remove from favourites")
                }
            }
        }
    }
}