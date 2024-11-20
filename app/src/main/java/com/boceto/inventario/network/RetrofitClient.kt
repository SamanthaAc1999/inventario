package com.boceto.inventario.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create()) //Parsea el json
        .build()

    fun createInventoryApiClient(): InventoryApiService {
        return retrofit.create(InventoryApiService::class.java)
    }
}