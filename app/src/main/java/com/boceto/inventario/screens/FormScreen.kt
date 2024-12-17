package com.boceto.inventario.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.navigate.Routes
import com.boceto.inventario.ui.theme.InventarioTheme

data class Bodega(val id: String, val nombre: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = "Formulario Conteo", style = MaterialTheme.typography.titleMedium) },
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.navigate(Routes.HomeScreen.routes)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color(0xFF151635),
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FormList(navHostController)
        }
    }
}

@Composable
fun FormList(navHostController: NavHostController) {
    val bodegas = listOf(
        Bodega("01", "Distribuidora"),
        Bodega("02", "Feria Libre"),
        Bodega("03", "Mega Americas"),
        Bodega("04", "Mega Chaullabamba"),
        Bodega("05", "Mega Remigio"),
        Bodega("06", "Mega Ricaurte"),
        Bodega("07", "Mega Saraguro")
    )


    var selectedBodegaId by remember { mutableStateOf<String?>(null) }
    var selectedBodegaNombre by remember { mutableStateOf("") }
    var seccion: String by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = "Bodega")
        Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, color = Color(0xFFE2DAD6), shape = RoundedCornerShape(8.dp))
                        .padding(4.dp)
                ) {
                    Text(
                        text = if (selectedBodegaNombre.isEmpty()) "Selecciona una bodega" else selectedBodegaNombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                            .padding(12.dp)
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    ) {
                        bodegas.forEach { bodega ->
                            DropdownMenuItem(
                                text = { Text(bodega.nombre) },
                                onClick = {
                                    selectedBodegaId = bodega.id
                                    selectedBodegaNombre = bodega.nombre
                                    expanded = false

                                    Log.d("BodegaSeleccionada", "ID: ${selectedBodegaId}, Nombre: ${selectedBodegaNombre}")
                                }
                            )
                        }
                    }
                }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Sección")
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = seccion,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    seccion = newValue
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                selectedBodegaId?.let { id ->
                    navHostController.navigate(Routes.InventoryScreen.createRoute(id.toString()))
                }
                Log.d("BodegaSeleccionad2", "ID: ${selectedBodegaId}, Nombre: ${selectedBodegaNombre}")            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF151635)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedBodegaId != null
        ) {
            Text(
                text = "Ingresar"
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreviewScreen() {
    InventarioTheme {
        FormScreen(rememberNavController())
    }
}
