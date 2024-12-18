package com.boceto.inventario.screens.inventory

import com.boceto.inventario.network.TableList


data class TableUiState(
    val value: List<TableList> = emptyList(),
    val notFoundProduct: Boolean = false,
    val messageNotFoundProduct: String = "",
    val isLoading: Boolean = false
)
