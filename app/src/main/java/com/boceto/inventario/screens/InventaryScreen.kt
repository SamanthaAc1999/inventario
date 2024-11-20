package com.boceto.inventario.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.boceto.inventario.ui.theme.InventarioTheme
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
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
                        onClick = {/*TODO*/},
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
                modifier = Modifier.padding(horizontal = 20.dp)
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
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF151635)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement= Arrangement.SpaceEvenly
    ){
        TextField(
            value = "",
            onValueChange = {},
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
            imageVector = Icons.Filled.Info,
            contentDescription = "Icon Camera",
            tint = Color.White,
        )
    }
}

@Composable
fun CardItemInformation() {
  Card (
      colors = CardDefaults.cardColors(
          containerColor = Color(0xFFECF1F7),
          contentColor = Color.Black
      ),
      modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
  ) {
      Column {
          Text(
              text = "NOMBRE DEL CONTENIDO",
              modifier = Modifier. padding(5.dp)
          )
          Column (
              modifier = Modifier.padding(5.dp)
          ) {
              Text(text = "")
              Text(text = "Saldo: 55")
              Text(text = "Costo: 55")
              Spacer(modifier = Modifier.width(10.dp))
              Row (
                  verticalAlignment = Alignment.CenterVertically
              ) {
                  Text(text = "Cant.")
                  TextField(
                      value = "",
                      onValueChange = {},
                      modifier = Modifier
                          .height(30.dp)
                          .width(80.dp)
                  )
                  Spacer(modifier = Modifier.width(5.dp))
                  Button(
                      onClick = {/*TODO*/ },
                      colors = ButtonDefaults.buttonColors(
                          containerColor = Color(0xFF151635)
                      )
                  ) {
                      Text(text = "Ok")
                  }
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
        InventoryScreen()
    }
}