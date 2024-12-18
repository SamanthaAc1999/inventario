package com.boceto.inventario.screens.form

import com.boceto.inventario.network.WarehousesList

data class FormUiState(
    val value: List<WarehousesList> = emptyList(),
    val notFoundProduct: Boolean = false,
    val messageNotFoundProduct: String = "",
    val isLoading: Boolean = false
)
