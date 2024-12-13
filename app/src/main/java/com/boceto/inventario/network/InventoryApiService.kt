package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InventoryApiService {
    @GET("/items/porbodegacodigobarra/{codeBar}/{whsCode}")

    fun getProducts(
        @Path("codeBar")
        codeBar: String,
        @Path("whsCode")
        whsCode: String
    ): Call<ProductResponse>
}

data class ProductResponse(
    @SerializedName("rc")
    val rc: Int,
    @SerializedName("message")
    val messages: String,
    @SerializedName("value")
    val value: ValueItem,
)

data class ValueItem(
    @SerializedName("codigoSAP")
    val code: String,
    @SerializedName("nombreSAP")
    val name: String,
    @SerializedName("codigoBarra")
    val codeBarP: String,
    @SerializedName("tieneIVA")
    val iva: Boolean,
    @SerializedName("unidadInventario")
    val unidad: String,
    @SerializedName("saldo")
    val saldo: Int,
    @SerializedName("costo")
    val costo: Double,
)

