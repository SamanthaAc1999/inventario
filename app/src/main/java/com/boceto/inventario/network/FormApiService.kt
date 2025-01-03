package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FormApiService {
    @GET("/warehouses")
    fun getWarehouses(): Call<WarehousesResponse>

    @GET("/warehouses/config/exists")
    fun sendWarehouseConfig(
        @Query("warehouseCode") warehouseCode: String,
        @Query("seccion") seccion: Int
    ): Call<SendResponse>

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


data class SendRequest(
    @SerializedName("idBodega")
    val idBodega: String,
    @SerializedName("idSeccion")
    val idSeccion: Int
)

data class SendResponse(
    @SerializedName("rc")
    val rc: Int,
    @SerializedName("message")
    val messages: String,
    @SerializedName("value")
    val value: Any? = null
)
