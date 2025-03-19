import android.util.Log
import com.example.androidarchitecturemvvm.Data.model.Products
import com.example.androidarchitecturemvvm.Data.remote.ApiService
import com.example.androidarchitecturemvvm.Data.remote.RemoteDataSource

class ProductsRemoteDataSource(private val service: ApiService) : RemoteDataSource {
    override suspend fun getAllProducts(): List<Products?> {
        try {
            val response = service.getProducts()
            if (response.isSuccessful && response.body() != null) {
                Log.d("ProductsRemoteDataSource", "Products fetched successfully: ${response.body()!!.productsList.size} items")
                return response.body()!!.productsList
            } else {
                Log.d("ProductsRemoteDataSource", "Failed to fetch products: ${response.code()} ${response.message()}")
                return emptyList()
            }
        } catch (ex: Exception) {
            Log.d("ProductsRemoteDataSource", "Exception: ${ex.message}")
            return emptyList()
        }
    }
}
