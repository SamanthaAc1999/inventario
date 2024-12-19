package com.boceto.inventario.screens.inventory

import com.boceto.inventario.network.TableList
import com.boceto.inventario.network.ValueItem
import com.boceto.inventario.network.ValueList

data class InventoryUiState(
    val value: ValueItem ?= null,
    val notFoundProduct: Boolean = false,
    val messageNotFoundProduct: String = "",
    //SEARCH
    val isLoading: Boolean = false,
    val searchvalue: List<ValueList> = emptyList(),
    //table
    val tablevalue: List<TableList> = emptyList(),

    val dialogOpen: Boolean = false, // Estado para el diálogo
    val scanInputValue: String = "", // Valor del campo de escaneo
    val selectedWarehouse: String = "" // Almacén seleccionado
)
