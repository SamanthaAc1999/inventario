package com.boceto.inventario.screens.inventory

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.boceto.inventario.network.ListProductResponse
import com.boceto.inventario.network.ProductResponse
import com.boceto.inventario.network.RetrofitClient
import com.boceto.inventario.network.SendItemRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState: StateFlow<InventoryUiState> = _uiState
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setLoadingState(loading: Boolean) {
        _isLoading.value = loading
    }

    fun getProduct(codeProduct: String, idBodega: String, seccion: Int) {
        val apiService = RetrofitClient.createInventoryApiClient()
        val call = apiService.getProducts(codeProduct, idBodega, seccion)
        Log.d("Encontrado", "URL: ${call.request().url()}")
       call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                Log.d("Encontrado", "Body: ${response.body()?.toString() ?: "Cuerpo vacío"}")
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.rc == 1) {
                        Log.d("Encontrado", "Encontrado")
                        Toast.makeText(context, "Producto Encontrado", Toast.LENGTH_SHORT).show()
                        _uiState.value = _uiState.value.copy(value = result.value)
                    } else {
                        // Resultado fallido
                        Log.d("Scan", "No Encontrado")
                        Toast.makeText(context, "Producto No Encontrado", Toast.LENGTH_SHORT).show()
                        _uiState.value = _uiState.value.copy(
                            notFoundProduct = true,
                            messageNotFoundProduct = result?.messages ?: "Ocurrió un error"
                        )
                    }
                } else {
                    // Hubo error
                    Log.d("Encontrado", "Hubo error")
                    _uiState.value = _uiState.value.copy(
                        notFoundProduct = true,
                        messageNotFoundProduct = "Ocurrió un error"
                    )
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("Encontrado", "Error al obtener productos: ${t.message}", t)
                if (t is IOException) {
                    Log.e("Encontrado", "Error de conexión: ${t.message}")
                } else {
                    Log.e("Encontrado", "Error desconocido: ${t.message}")
                }
            }
        })
    }

    fun FetchInventoryCounting(warehouseCode: String, seccion: Int) {
        val apiService = RetrofitClient.createInventoryApiClient()

        _uiState.value = _uiState.value.copy(isLoading = true)

        apiService.getInventoryCounting( warehouseCode, seccion).enqueue(object : Callback<ListProductResponse> {
            override fun onResponse(
                call: Call<ListProductResponse>,
                response: Response<ListProductResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if(result?.rc == 1) {
                        Log.d("Inventario QR", "${response.body()}")
                       val listProductUpdate = result.value.map {
                           TableItem(
                               name = it.nombreItem,
                               quantify = it.cantidad,
                               code = it.codigoBarra
                           )
                       }
                        _uiState.value = _uiState.value.copy(
                            listProductUpdate = listProductUpdate,
                            isLoading = false,
                            notFoundProduct = false
                        )
                    }
                } else {
                    Log.e("Inventario", "Error al obtener inventario: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ListProductResponse>, t: Throwable) {
                Log.e("Inventario", "Error de conexión: ${t.message}", t)
                _uiState.value = _uiState.value.copy(messageNotFoundProduct = "Error de conexión")
            }
        })
    }

    fun sendItem(
        idBodega: String,
        idSeccion: Int,
        idItem: String,
        cantidad: Double,
        saldo: Double
    ) {
        val apiService = RetrofitClient.createInventoryApiClient()
        setLoadingState(true)
        val request= SendItemRequest(idBodega = idBodega, idSeccion = idSeccion, idItem = idItem, cantidad = cantidad, saldo = saldo)
        val call = apiService.SendItem(request)
        Log.d("Inventario QR", "${cantidad} ")
        Log.d("Inventario QR", "${request} ")
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                setLoadingState(false)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Item enviado exitosamente")
                    Toast.makeText(context, "Cantidad Agregada con éxito", Toast.LENGTH_SHORT).show()
                    FetchInventoryCounting(idBodega, idSeccion)
                    _uiState.value = _uiState.value.copy(value = null)
                } else {
                    Log.e("API_ERROR", "Error al enviar el item: ${response.code()}")
                    Toast.makeText(context, "Error envio: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                setLoadingState(false)
                Log.e("API_ERROR", "Error al enviar el item: ${t.message}", t)
                Toast.makeText(context, "Error envio: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun updateItem(
        idBodega: String,
        idSeccion: Int,
        idItem: String,
        cantidad: Double,
        saldo: Double
    ) {
        val apiService = RetrofitClient.createInventoryApiClient()

        apiService.UpdateItem(SendItemRequest(idBodega =idBodega, idSeccion =idSeccion, idItem =idItem, cantidad =cantidad, saldo = saldo)).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Item actualizado exitosamente")
                    Toast.makeText(context, "Cantidad Actualizada con éxito", Toast.LENGTH_SHORT).show()
                    FetchInventoryCounting(idBodega, idSeccion)
                    _uiState.value = _uiState.value.copy(value = null)
                } else {
                    Log.e("API_ERROR", "Error al actualizar el item: ${response.code()}")
                    Toast.makeText(context, "Error envio: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("API_ERROR", "Error al actualizar el item: ${t.message}", t)
                Toast.makeText(context, "Error envio: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
