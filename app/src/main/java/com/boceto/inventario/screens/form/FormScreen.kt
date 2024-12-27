package com.boceto.inventario.screens.form

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.boceto.inventario.navigate.Routes
import com.boceto.inventario.network.WarehousesList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    navHostController: NavHostController,
    viewModel: FormViewModel = hiltViewModel() // Inyectamos el ViewModel
) {
    // Observar los cambios del estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Llamar a la API al iniciar
    LaunchedEffect(Unit) {
        viewModel.getWarehouses()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Formulario Conteo",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
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
            // Pasar la lista de bodegas al Composable FormList
            FormList(navHostController, uiState.value)
        }
    }
}

@Composable
fun FormList(navHostController: NavHostController, bodegas: List<WarehousesList>) {
    var selectedBodegaId by remember { mutableStateOf<String?>(null) }
    var selectedBodegaNombre by remember { mutableStateOf("") }
    var seccion: Int? by remember { mutableStateOf(null) }
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
                .border(1.dp, color = Color(0xFF3C3D37), shape = RoundedCornerShape(7.dp))
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
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(2.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                    )
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
        OutlinedTextField(
            value = seccion?.toString() ?: "",
            placeholder = { Text(text = "Seleccione una sección") },
            onValueChange = { newValue ->
                seccion = newValue.toIntOrNull()
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
                    seccion?.let { sec ->
                        navHostController.navigate(Routes.InventoryScreen.createRoute(id, sec))
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF151635)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedBodegaId != null && seccion != null
        ) {
            Text(
                text = "Ingresar"
            )
        }
    }
}