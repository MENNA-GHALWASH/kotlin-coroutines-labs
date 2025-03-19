package com.example.androidarchitecturemvvm.Favourites.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidarchitecturemvvm.Data.Repo.Repo
import com.example.androidarchitecturemvvm.Data.model.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo: Repo) : ViewModel() {

    private val _favouriteProducts = MutableStateFlow<List<Products>>(emptyList())
    val Products: StateFlow<List<Products>> get() = _favouriteProducts

    private val _message = MutableStateFlow<String>("Loading..")
    val message: StateFlow<String> get() = _message

    fun fetchFavourites() {
        viewModelScope.launch {
            try {
                repo.getAllProducts(false).collect { favProducts ->
                    Log.d("FavoritesViewModel", "Fetched Favourites: $favProducts")
                    _favouriteProducts.value = favProducts as List<Products>
                }
            } catch (e: Exception) {
                Log.e("FavoritesViewModel", "Error fetching favourites: ${e.message}")
                _message.value = e.message ?: "An error occurred"
            }
        }
    }


    fun deleteProduct(products: Products) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.deleteProduct(products)
                fetchFavourites()
                _message.value = ("Removed from favourites successfully")
            } catch (e: Exception) {
                _message.value = (e.message ?: "An error occurred")
            }
        }
    }
}

class FavouritesViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel(repo) as T
    }
}
