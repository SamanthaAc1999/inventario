package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApiService {
    @GET("/items/porbodeganombreitem/{itemName}/{whsCode}")

    fun getProductsName(
        @Path("itemName")
        itemName: String,
        @Path("whsCode")
        whsCode: String
    ): Call<SearchResponse>
}

data class SearchResponse(
    @SerializedName("rc")
    val rc: Int,
    @SerializedName("message")
    val messages: String,
    @SerializedName("value")
    val value: List<ValueList>,
)

data class ValueList(
    @SerializedName("codigoSAP")
    val code: String,
    @SerializedName("nombreSAP")
    val name: String,
    @SerializedName("codigoBarra")
    val codeBars: Int,
    @SerializedName("tieneIVA")
    val iva: Boolean,
    @SerializedName("unidadInventario")
    val unidad: String,
    @SerializedName("saldo")
    val saldo: Double,
    @SerializedName("costo")
    val costo: Double,
)

