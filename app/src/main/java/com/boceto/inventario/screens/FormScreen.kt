package com.boceto.inventario.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var bodega: String by remember { mutableStateOf("") }
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
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, color = Color(0xFFE2DAD6), shape = RoundedCornerShape(8.dp))
                .padding(4.dp)
        ){
            Text(
                text = if (bodega.isEmpty()) "Selecciona una bodega" else bodega,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(12.dp)
            )

            DropdownMenu (
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                listOf("Distribuidora", "Feria Libre", "Mega Americas", "Mega Chaullabamba", "Mega Remigio", "Mega Ricaurte", "Mega Saraguro").forEach { ciudad ->
                    DropdownMenuItem(
                        text = { Text(ciudad) },
                        onClick = {
                            bodega = ciudad
                            expanded = false
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
                navHostController.navigate(Routes.InventoryScreen.routes)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF151635)
            ),
            modifier = Modifier.fillMaxWidth()
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