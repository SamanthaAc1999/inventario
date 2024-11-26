package com.boceto.inventario.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InventoryApiService {
    @GET("/products")
    fun getProducts(): Call<ProductResponse>

    @POST("/formulario")
    fun sendListForm(@Body formRequest: FormRequest): Call<ProductResponse>

    @POST("/producto")
    fun sendCodeProduct(@Body producto: Producto): Call<ProductResponse>

    @POST("/guardar")
    fun sendList(@Body guardar: Guardar): Call<ProductResponse>
}

data class ProductResponse(
    val id: Int,
    val name: String,
    val balance: Int,
    val quantity: Int,
    val price: Double
)

data class FormRequest(
    val bodega: String,
    val seccion: String
)

data class Producto(
    val code: Int
)

data class Guardar(
    val id: Int,
    val name: String,
    val quantity: Int
)
