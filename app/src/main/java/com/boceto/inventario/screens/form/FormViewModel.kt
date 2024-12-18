package com.boceto.inventario.screens.form

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boceto.inventario.network.RetrofitClient
import com.boceto.inventario.network.SearchResponse
import com.boceto.inventario.network.WarehousesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.Normalizer.Form
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(FormUiState())
    val uiState: StateFlow<FormUiState> = _uiState

    // Función para obtener productos
    fun getWarehouses() {
        val apiService = RetrofitClient.createFormApiClient()

        // Actualizamos el estado a "Cargando"
        _uiState.value = _uiState.value.copy( )

        apiService.getWarehouses().enqueue(object : Callback<WarehousesResponse> {
            override fun onResponse(
                call: Call<WarehousesResponse>,
                response: Response<WarehousesResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.rc == 1) {
                        _uiState.value = _uiState.value.copy(
                            value = result.value,
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

            override fun onFailure(call: Call<WarehousesResponse>, t: Throwable) {
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
