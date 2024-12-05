package com.boceto.inventario.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.boceto.inventario.ui.theme.InventarioTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.ScannerActivity
import com.boceto.inventario.navigate.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(navHostController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(title = {
                CenterAlignedTopAppBar(title = {
                    Text(text = "Ingreso Conteo")
                }
                )
            },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.navigate(Routes.FormScreen.routes)
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF151635), contentColor = Color.White),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            layoutSearchItem()
            Column (
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Spacer(modifier =Modifier.height(20.dp))
                CardItemInformation()
                Spacer(modifier =Modifier.height(20.dp))
                Text(text = "LISTADO CARGADO")
                Spacer(modifier =Modifier.height(20.dp))
                CardTableItems()
            }
        }
    }
}

@Composable
fun layoutSearchItem(){
    val context = LocalContext.current
    val scannerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data = result.data
            val scanResult = data?.getStringExtra("SCAN_RESULT")
            println(scanResult)
        }
    }
    var codigoBarras: String by remember { mutableStateOf("") }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF151635)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement= Arrangement.SpaceEvenly
    ){
        TextField(
            value = codigoBarras,
            onValueChange = {codigoBarras = it},
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
            modifier = Modifier
                .padding(5.dp)
                .background(Color(0xFFEEF1F5)),
            placeholder = {
                Text(text = "0000001")
            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Icon Camera",
            tint = Color.White,
            modifier = Modifier.clickable {
                val intent = Intent(context,ScannerActivity::class.java)
                scannerLauncher.launch(intent)
            }
        )
    }
}

@Composable
fun CardItemInformation() {
    var cant: String by remember { mutableStateOf("") }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFECF1F7),
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Nombre del producto",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Código:", fontSize = 16.sp)
                Text(text = "7861024611060", fontSize = 16.sp)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Saldo:", fontSize = 16.sp)
                Text(text = "55", fontSize = 16.sp)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text(text = "Cant:", fontSize = 16.sp)

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = cant,
                    onValueChange = { cant = it },
                    label = { Text("Ingrese Cantidad", fontSize = 14.sp) },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Button(
                    onClick = { println("Cantidad agregada: $cant") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151635)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Agr.", color = Color.White, fontSize = 14.sp)
                }

                Button(
                    onClick = { cant = "" },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E7D98)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Reing.", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}


@Composable
fun CardTableItems(){
    Card (
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFECF1F7),
            contentColor = Color.Black
        ),
    modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp) ) {
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InventoryScreenPreview(){
    InventarioTheme {
        InventoryScreen(rememberNavController())
    }
}

