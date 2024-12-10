package com.boceto.inventario.screens.inventory

import androidx.lifecycle.ViewModel
import com.boceto.inventario.network.ProductResponse
import com.boceto.inventario.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InventoryViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState: StateFlow<InventoryUiState> = _uiState

    fun getProduct(codeProduct: String, idBodega: String){
        val apiService = RetrofitClient. createInventoryApiClient()

        apiService.getProducts(codeProduct, idBodega).enqueue(object: Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val result= response.body()
                    if(result?.rc == 1){
                        _uiState.value = _uiState.value.copy(value = result.value)
                    } else {
                        //Resultado Fallido
                        _uiState.value = _uiState.value.copy(notFoundProduct = true, messageNotFoundProduct = result?.messages ?: "Ocurrio un error")
                    }
                } else {
                    //Hubo Error
                    _uiState.value = _uiState.value.copy(notFoundProduct = true, messageNotFoundProduct = "Ocurrio un error")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}