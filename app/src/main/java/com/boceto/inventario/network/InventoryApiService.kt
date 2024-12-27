package com.boceto.inventario.network

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryApiService {

    //Solicitud 1 -Obtener valores cuando se escanea el código
    @GET("/items/porbodegacodigobarra/{codeBar}/{whsCode}/{seccion}")
    fun getProducts(
        @Path("codeBar") codeBar: String,
        @Path("whsCode") whsCode: String,
        @Path("seccion") seccion: Int,
    ): Call<ProductResponse>


    //Solicitud 2 - Obtener listado de productos cuando se busca por nombre
    @GET("/items/porbodeganombreitem/{itemName}/{whsCode}/{seccion}")
    fun getProductsName(
        @Path("itemName")
        itemName: String,
        @Path("whsCode")
        whsCode: String,
        @Path("seccion")
        seccion: Int
    ): Call<SearchResponse>


    //Solicitud 3 - Obtener listado de productos agregados en esa sección
    @GET("/inventorycounting")
    fun getInventoryCounting(
        @Query("warehouseCode")
        warehouseCode: String,
        @Query("seccion")
        seccion: Int
    ): Call<ListProductResponse>


    //Solicitud 4 - Enviar datos del producto y la cantidad cuando agrega el producto (Puede ser primera vez o ir sumando)
    @POST("/inventorycounting")
    fun SendItem(
        @Body SendItemRequest: SendItemRequest
    ): Call<ResponseBody>


    //Solicitud 5 - Enviar datos del producto y la cantidad cuando quiere reemplazar el valor actual de la cantidad.
    @PUT("/inventorycounting")
    fun UpdateItem(
        @Body SendItemRequest: SendItemRequest
    ): Call<ResponseBody>
}

//Solicitud 1
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
    @SerializedName("totalSeccion")
    val totalSeccion: Double,
    @SerializedName("totalGeneral")
    val totalGeneral: Double
)

//Solicitud 2
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
    val codeBars: String,
    @SerializedName("tieneIVA")
    val iva: Boolean,
    @SerializedName("unidadInventario")
    val unidad: String,
    @SerializedName("saldo")
    val saldo: Double,
    @SerializedName("costo")
    val costo: Double,
)

//Solicitud 3
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


//Solicitud 4 -5
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




