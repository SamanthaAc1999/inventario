package com.boceto.inventario.screens.form

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boceto.inventario.navigate.Routes
import com.boceto.inventario.network.RetrofitClient
import com.boceto.inventario.network.SearchResponse
import com.boceto.inventario.network.SendItemRequest
import com.boceto.inventario.network.SendRequest
import com.boceto.inventario.network.SendResponse
import com.boceto.inventario.network.WarehousesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.Normalizer.Form
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

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

    fun sendWarenhouse(warehouseCode: String, seccion: Int, onSuccess: () -> Unit) {
        val apiService = RetrofitClient.createFormApiClient()
        val call = apiService.sendWarehouseConfig(warehouseCode, seccion)

        // Log para visualizar la URL
        Log.d("API_REQUEST", "URL: ${call.request().url()}")

        call.enqueue(object : Callback<SendResponse> {
                override fun onResponse(
                    call: Call<SendResponse>,
                    response: Response<SendResponse>
                ){
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result?.rc == 0) {
                            Log.d("API_SUCCESS", "Llego aqui")
                            onSuccess()
                        } else {
                            Log.d("API_SUCCESS", "ERROR")
                            Toast.makeText(context, "La sección fue finalizada", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Log.d("API_SUCCESS", "ERROR ENVIO")
                        Log.e("API_ERROR", "Error al enviar el item: ${response.code()}")
                        Toast.makeText(context, "Error al enviar el item", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SendResponse>, t: Throwable) {
                    Log.e("API_ERROR", "Error al enviar el item: ${t.message}", t)
                    Toast.makeText(context, "Error envio: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }



}
