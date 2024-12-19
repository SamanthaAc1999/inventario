package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface InventoryApiService {
    @GET("/items/porbodegacodigobarra/{codeBar}/{whsCode}")
    fun getProducts(
        @Path("codeBar") codeBar: String,
        @Path("whsCode") whsCode: String
    ): Call<ProductResponse>

    @GET("/inventorycounting/{warehouseCode}/{seccion}")
    fun getProductsTable(
        @Path("warehouseCode") warehouseCode: String,
        @Path("seccion") seccion: Int
    ): Call<ListProductResponse>

    @POST("/inventorycounting")
    fun SendItem(
        @Body request: SendItemRequest
    ): Call<ResponseBody>

    @PUT("/inventorycounting")
    fun UpdateItem(
        @Body request: SendItemRequest
    ): Call<ResponseBody>
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

data class SendItemRequest(
    @SerializedName("idBodega")
    val idBodega: String,
    @SerializedName("idSeccion")
    val idSeccion: Int,
    @SerializedName("idItem")
    val idItem: String,
    @SerializedName("cantidad")
    val cantidad: Int,
    @SerializedName("saldo")
    val saldo: Int
)
