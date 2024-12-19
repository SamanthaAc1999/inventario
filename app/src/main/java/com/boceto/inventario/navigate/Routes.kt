package com.boceto.inventario.navigate

 sealed class Routes (val routes: String) {
     data object LoginScreen: Routes(routes = "splash_screen")
     data object HomeScreen: Routes(routes = "home_screen")
     data object FormScreen: Routes(routes = "form_screen")
     data object InventoryScreen: Routes(routes = "inventory_screen/{id_bodega}/{seccion}")
     fun createRoute(
         idBodega: String,
         seccion: String
     ): String {
         return "inventory_screen/${idBodega}/${seccion}"
     }
}