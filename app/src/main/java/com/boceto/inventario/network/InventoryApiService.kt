package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryApiService {
    @GET("/items/{codeBar}/{codeWareHouse}")

    fun getProducts(
        @Path("codeBar")
        codeBar: String,
        @Path("codeWareHouse")
        codeWareHouse: String
    ): Call<ProductResponse>
}

data class ProductResponse(
    @SerializedName("rc")
    val rc: Int,
    @SerializedName("message")
    val messages: String,
    @SerializedName("value")
    val value: List<ValueItem>,
)

data class ValueItem(
    @SerializedName("codigoSAP")
    val code: String,
    @SerializedName("nombreSAP")
    val name: String,
    @SerializedName("saldo")
    val saldo: Int,
    @SerializedName("costo")
    val costo: Double,
    @SerializedName("cantidad")
    val cantidad: Int,
)

