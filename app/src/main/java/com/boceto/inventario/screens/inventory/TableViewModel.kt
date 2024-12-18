package com.boceto.inventario.screens.inventory

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boceto.inventario.network.ListProductResponse
import com.boceto.inventario.network.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TableUiState()) // Estado inicial
    val uiState: StateFlow<TableUiState> = _uiState

    // Función para obtener productos
    fun getProducts(seccion: Int, idBodega: String) {
        val apiService = RetrofitClient.createTableApiClient()

        // Actualizamos el estado a "Cargando"
        _uiState.value = _uiState.value.copy(isLoading = true )

        apiService.getProductsTable(idBodega, seccion).enqueue(object : Callback<ListProductResponse> {
            override fun onResponse(
                call: Call<ListProductResponse>,
                response: Response<ListProductResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.rc == 1) {
                        // Si la respuesta es exitosa y rc es 1, actualizamos la lista de productos
                        _uiState.value = _uiState.value.copy(
                            value = result.value, // Asignamos la lista de productos
                            isLoading = false,
                            notFoundProduct = false
                        )
                    } else {
                        // Resultado fallido
                        _uiState.value = _uiState.value.copy(
                            notFoundProduct = true,
                            isLoading = false,
                            messageNotFoundProduct = result?.messages ?: "Ocurrió un error"
                        )
                    }
                } else {
                    // Hubo un error en la respuesta
                    _uiState.value = _uiState.value.copy(
                        notFoundProduct = true,
                        isLoading = false,
                        messageNotFoundProduct = "Ocurrió un error"
                    )
                }
            }

            override fun onFailure(call: Call<ListProductResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error al obtener productos: ${t.message}", t)

                if (t is IOException) {
                    Log.e("API_ERROR", "Error de conexión: ${t.message}")
                } else {
                    Log.e("API_ERROR", "Error desconocido: ${t.message}")
                }

                // En caso de fallo en la llamada, actualizamos el estado con el error
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    notFoundProduct = true,
                    messageNotFoundProduct = "Error de conexión o desconocido"
                )
            }
        })
    }
}
