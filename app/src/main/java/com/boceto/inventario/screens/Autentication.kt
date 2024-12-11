package com.boceto.inventario.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.ui.theme.InventarioTheme

@Composable
fun AutenticationForm(rememberNavController: NavHostController) {
    TextField(
        value = "seccio",
        onValueChange = {
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AutenticationPreviewScreen(){
    InventarioTheme {
        AutenticationForm(rememberNavController())
    }
}
