package com.boceto.inventario.navigate

 sealed class Routes (val routes: String) {
     data object LoginScreen: Routes(routes = "splash_screen")
     data object InventoryScreen: Routes(routes = "inventory_screen")
}