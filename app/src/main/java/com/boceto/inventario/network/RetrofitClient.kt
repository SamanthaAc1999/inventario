package com.boceto.inventario.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.10.212:8085/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createInventoryApiClient(): InventoryApiService {
        return retrofit.create(InventoryApiService::class.java)
    }
}