package com.boceto.inventario.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InventoryApiService {
    @GET("/products")
    fun getProducts(): Call <ProductResponse>
}

data class ProductResponse(
    val id: Int,
    val name: String,
    val balance: Int,
    val quantity: Int,
    val price: Double
)