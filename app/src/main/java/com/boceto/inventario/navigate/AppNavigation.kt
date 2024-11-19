package com.boceto.inventario.navigate
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.screens.InventoryScreen
import com.boceto.inventario.screens.LoginScreen

@Composable
fun AppNavigation () {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.InventoryScreen.routes) {
        composable(Routes.InventoryScreen.routes){
            InventoryScreen()
        }

        composable(Routes.LoginScreen.routes){
            LoginScreen()
        }
    }
}


