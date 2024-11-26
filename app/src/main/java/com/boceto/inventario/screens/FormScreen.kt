package com.boceto.inventario.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boceto.inventario.ui.theme.InventarioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "Formulario Conteo")
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /* TODO */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color(0xFF151635),
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = null
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
            FormList()
        }
    }
}



@Composable
fun FormList() {
    var bodega: String by remember { mutableStateOf("") }
    var seccion: String by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
       Text(text = "Bodega")
        Spacer(modifier = Modifier.height(10.dp))
      TextField(
          value = bodega,
          onValueChange = {bodega = it},
          modifier = Modifier.padding(2.dp).fillMaxWidth(),
          placeholder = {
              Text(
                  text = "Cuenca"
              )
          }
      )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Sección")
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = seccion,
            onValueChange = {seccion = it},
            modifier = Modifier.padding(2.dp).fillMaxWidth(),
            placeholder = {
                Text(
                    text = "A-1"
                )
            }

        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {/*TODO*/},
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
        FormScreen()
    }
}
