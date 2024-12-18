package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TableApiService {
    @GET("/inventorycounting/{warehouseCode}/{seccion}")

    fun getProductsTable(
        @Path("warehouseCode ")
        warehouseCode: String,
        @Path("seccion ")
        seccion: Int
    ): Call<ListProductResponse>
}

data class ListProductResponse(
    @SerializedName("rc")
    val rc: Int,
    @SerializedName("message")
    val messages: String,
    @SerializedName("value")
    val value: List<TableList>,
)

data class TableList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("idLinea")
    val idLinea: Int,
    @SerializedName("idBodega")
    val idBodega: String,
    @SerializedName("idSeccion")
    val idSeccion: Int,
    @SerializedName("idItem")
    val idItem: String,
    @SerializedName("nombreItem")
    val nombreItem: String,
    @SerializedName("unidadMedida")
    val unidadMedida: String,
    @SerializedName("codigoBarra")
    val codigoBarra: String,
    @SerializedName("cuentaContable")
    val cuentaContable: String,
    @SerializedName("tieneIVA")
    val tieneIVA: Boolean,
    @SerializedName("exportado")
    val exportado: Boolean,
    @SerializedName("cantidad")
    val cantidad: Int,
    @SerializedName("costo")
    val costo: Float,
    @SerializedName("valor")
    val valor: Float,
    @SerializedName("diferencia")
    val diferencia: Int,
    @SerializedName("saldo")
    val saldo: Int,
)

