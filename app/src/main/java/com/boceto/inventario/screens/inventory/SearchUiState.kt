package com.boceto.inventario.screens.inventory

import com.boceto.inventario.network.ValueItem
import com.boceto.inventario.network.ValueList

data class SearchUiState(
    val value: List<ValueList> = emptyList(),
    val notFoundProduct: Boolean = false,
    val messageNotFoundProduct: String = ""
)
