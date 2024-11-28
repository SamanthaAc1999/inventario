package com.boceto.inventario.navigate
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.screens.FormScreen
import com.boceto.inventario.screens.HomeScreen
import com.boceto.inventario.screens.InventoryScreen
import com.boceto.inventario.screens.LoginScreen

@Composable
fun AppNavigation () {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.InventoryScreen.routes) {
        composable(Routes.LoginScreen.routes){
            LoginScreen()
        }

        composable(Routes.HomeScreen.routes){
            HomeScreen()
        }

        composable(Routes.FormScreen.routes){
            FormScreen(navController)
        }

        composable(Routes.InventoryScreen.routes){
            InventoryScreen(navController)
        }
    }
}


