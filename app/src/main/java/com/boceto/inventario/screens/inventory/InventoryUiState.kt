package com.boceto.inventario.screens.inventory

import com.boceto.inventario.network.ValueItem

data class InventoryUiState(
    val value: ValueItem ?= null ,
    val notFoundProduct: Boolean = false,
    val messageNotFoundProduct: String = ""
)
