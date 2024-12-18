package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface FormApiService {
    @GET("/warehouses")
    fun getWarehouses(): Call<WarehousesResponse>

}

data class WarehousesResponse(
    @SerializedName("rc")
    val rc: Int,
    @SerializedName("message")
    val messages: String,
    @SerializedName("value")
    val value: List<WarehousesList>,
)

data class WarehousesList(
    @SerializedName("id")
    val id: String,
    @SerializedName("nombre")
    val nombre: String,
)

