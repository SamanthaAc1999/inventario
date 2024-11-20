package com.boceto.inventario.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boceto.inventario.network.ProductResponse
import com.boceto.inventario.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InventoryViewModel: ViewModel() {
    fun getProduct(){
        val apiService = RetrofitClient. createInventoryApiClient()

        apiService.getProducts().enqueue(object: Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("TAG", "El nombre del producto es: ${response.body()?.name}")
                } else {
                    //Hubo error
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}