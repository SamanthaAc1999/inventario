package com.boceto.inventario.screens.inventory

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boceto.inventario.network.RetrofitClient
import com.boceto.inventario.network.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SeachViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState()) // Estado inicial
    val uiState: StateFlow<SearchUiState> = _uiState

    // Función para obtener productos
    fun getProducts(nameProduct: String, idBodega: String) {
        val apiService = RetrofitClient.createSearchApiClient()

        // Actualizamos el estado a "Cargando"
        _uiState.value = _uiState.value.copy( )

        apiService.getProductsName(nameProduct, idBodega).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.rc == 1) {
                        // Si la respuesta es exitosa y rc es 1, actualizamos la lista de productos
                        _uiState.value = _uiState.value.copy(
                            value = result.value, // Asignamos la lista de productos
                        )
                    } else {
                        // Resultado fallido
                        _uiState.value = _uiState.value.copy(
                            notFoundProduct = true,
                            messageNotFoundProduct = result?.messages ?: "Ocurrió un error"
                        )
                    }
                } else {
                    // Hubo un error en la respuesta
                    _uiState.value = _uiState.value.copy(
                        notFoundProduct = true,
                        messageNotFoundProduct = "Ocurrió un error"
                    )
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error al obtener productos: ${t.message}", t)

                if (t is IOException) {
                    Log.e("API_ERROR", "Error de conexión: ${t.message}")
                } else {
                    Log.e("API_ERROR", "Error desconocido: ${t.message}")
                }

                // En caso de fallo en la llamada, actualizamos el estado con el error
                _uiState.value = _uiState.value.copy(
                    notFoundProduct = true,
                    messageNotFoundProduct = "Error de conexión o desconocido"
                )
            }
        })
    }
}
