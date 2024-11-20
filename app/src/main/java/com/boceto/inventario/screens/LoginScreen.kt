package com.boceto.inventario.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.boceto.inventario.ui.theme.InventarioTheme

@Composable
fun LoginScreen(
){
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ){
        Text(
            text = "INVENTARIO",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    InventarioTheme {
        LoginScreen()
    }
}