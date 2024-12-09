package com.boceto.inventario.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InventoryApiService {
    @GET("warehouse/ObtenerSucursales")
    fun getWarenhouse(): Call<WarenhouseResponse>

    @GET("/products")
    fun getProducts(): Call<ProductResponse>

    @POST("/formRequest")
    fun sendListForm(@Body formRequest: FormRequest): Call<FormRequest>

    @POST("/infoProduct")
    fun sendCodeProduct(@Body infoProduct: InfoProduct): Call<InfoProduct>

    @POST("/saveList")
    fun sendList(@Body saveList: SaveList): Call<SaveList>
}

data class WarenhouseResponse(
    val whsCode: String,
    val whsName: String
)

data class ProductResponse(
    val id: Int,
    val name: String,
    val balance: Int,
    val quantity: Int,
    val price: Double
)

data class FormRequest(
    val bodega: String,
    val sec: String
)

data class InfoProduct(
    val code: Int
)

data class SaveList(
    val id: Int,
    val name: String,
    val quantity: Int
)
