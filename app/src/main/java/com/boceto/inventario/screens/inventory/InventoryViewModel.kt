package com.boceto.inventario.screens.inventory

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boceto.inventario.network.ProductResponse
import com.boceto.inventario.network.RetrofitClient
import com.boceto.inventario.network.SendItemRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState: StateFlow<InventoryUiState> = _uiState

    fun getProduct(codeProduct: String, idBodega: String) {
        val apiService = RetrofitClient.createInventoryApiClient()

        apiService.getProducts(codeProduct, idBodega).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.rc == 1) {
                        _uiState.value = _uiState.value.copy(value = result.value)
                    } else {
                        // Resultado fallido
                        _uiState.value = _uiState.value.copy(
                            notFoundProduct = true,
                            messageNotFoundProduct = result?.messages ?: "Ocurrió un error"
                        )
                    }
                } else {
                    // Hubo error
                    _uiState.value = _uiState.value.copy(
                        notFoundProduct = true,
                        messageNotFoundProduct = "Ocurrió un error"
                    )
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error al obtener productos: ${t.message}", t)

                if (t is IOException) {
                    Log.e("API_ERROR", "Error de conexión: ${t.message}")
                } else {
                    Log.e("API_ERROR", "Error desconocido: ${t.message}")
                }
            }
        })
    }

    fun fetchTableProducts(warehouseCode: String, seccion: Int) {
        val apiService = RetrofitClient.createInventoryApiClient()
        // Implementación pendiente...
    }

    fun sendItem(request: SendItemRequest) {
        val apiService = RetrofitClient.createInventoryApiClient()

        apiService.SendItem(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Item enviado exitosamente")
                    // Maneja la respuesta según sea necesario
                } else {
                    Log.e("API_ERROR", "Error al enviar el item: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("API_ERROR", "Error al enviar el item: ${t.message}", t)
            }
        })
    }

    fun updateItem(request: SendItemRequest) {
        val apiService = RetrofitClient.createInventoryApiClient()

        apiService.UpdateItem(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Item actualizado exitosamente")
                    // Maneja la respuesta según sea necesario
                } else {
                    Log.e("API_ERROR", "Error al actualizar el item: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("API_ERROR", "Error al actualizar el item: ${t.message}", t)
            }
        })
    }
}
