package com.example.androidarchitecturemvvm.AllProducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.androidarchitecturemvvm.Data.Repo.Repo
import com.example.androidarchitecturemvvm.Data.model.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repo: Repo) :ViewModel(){
    private val mutableProducts:MutableStateFlow<List<Products>> = MutableStateFlow(emptyList())
    val Products : StateFlow<List<Products>>  = mutableProducts // i don't understand why we did this

    private val mutablemssg:MutableStateFlow<String> = MutableStateFlow("")
    val message: StateFlow<String> = mutablemssg

    fun getProducts() {
        viewModelScope.launch {
            repo.getAllProducts(true)
                .collect { productList ->
                    mutableProducts.value = productList as List<Products>
                    Log.d("AllProductsViewModel", "Products fetched: ${productList.size}")
                }
        }
    }


    fun addToFav(products: Products?) {
        products?.let {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repo.addProduct(it) // 'it' is the non-null 'products'
                    mutableProducts.value = mutableProducts.value + it // Append product to list
                    mutablemssg.value = "Added successfully"
                } catch (ex: Exception) {
                    mutablemssg.value = ex.message.toString()
                }
            }
        }
    }

}

class AllProductsFactory(private val repo: Repo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return AllProductsViewModel(repo = repo) as T
    }
}