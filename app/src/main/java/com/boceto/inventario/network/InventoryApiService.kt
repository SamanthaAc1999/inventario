package com.boceto.inventario.network

import android.telecom.Call
import okhttp3.Request
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InventoryApiService {
    @GET("/products")
    fun getProducts(): Call <ProductResponse>

    @GET("/income")
    fun getList(): Call <Productslist>

    @POST("/create/form")
    fun createForm(
        @Body bodyRequest: Call <FormLogin>
    )

    @POST("/create/product")
    fun createForm(
        @Body bodyRequest: Call <ProductSend>
    )

    @POST("/finish")
    fun createForm(
        @Body bodyRequest: Call <Finished>
    )
}

data class ProductResponse(
    val id: Int,
    val name: String,
    val balance: Int,
    val quantily: Int,
    val price: Double
)

data class Productslist(
    val id: Int,
    val name: String,
    val quantily: Int
)

data class FormLogin(
    val store: String,
    val section: String,
)

data class ProductSend(
    val id: Int,
    val quantily: Int
)

data class Finished(
    val complete: Boolean
)