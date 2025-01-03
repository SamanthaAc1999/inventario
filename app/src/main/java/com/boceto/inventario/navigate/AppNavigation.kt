package com.boceto.inventario.navigate
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.screens.form.FormScreen
import com.boceto.inventario.screens.HomeScreen
import com.boceto.inventario.screens.inventory.InventoryScreen
import com.boceto.inventario.screens.LoginScreen

@Composable
fun AppNavigation () {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen.routes) {
        composable(Routes.LoginScreen.routes) {
            LoginScreen()
        }

        composable(Routes.HomeScreen.routes) {
            HomeScreen(navController)
        }

        composable(Routes.FormScreen.routes) {
            FormScreen(navController)
        }

        composable(Routes.InventoryScreen.routes) { backStackEntry ->
            val idBodega = backStackEntry.arguments?.getString("id_bodega") ?: "00"
            val seccion = backStackEntry.arguments?.getString("seccion")?.toInt() ?: 0
            InventoryScreen(navController, idBodega, seccion)
        }
    }
}


