package com.boceto.inventario.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.ui.theme.InventarioTheme
import com.boceto.inventario.navigate.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(navHostController: NavHostController) {
    var isDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            InventoryTopAppBar(navHostController, onSearchClick = { isDialogVisible = true })
        },
        content = { paddingValues ->
            InventoryContent(modifier = Modifier.padding(paddingValues))
        }
    )

    if (isDialogVisible) {
        SearchDialog(onDismiss = { isDialogVisible = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    navHostController: NavHostController,
    onSearchClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Ingreso Conteo", style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            IconButton(
                onClick = {
                    navHostController.navigate(Routes.FormScreen.routes)
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color(0xFF151635),
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier.semantics { contentDescription = "Buscar Producto" }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF151635),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun InventoryContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ScanField()
        CardItemInformation()
        CardTableItems()
    }
}

@Composable
fun ScanField() {
    var scanCode by remember { mutableStateOf("") }

    OutlinedTextField(
        value = scanCode,
        onValueChange = { scanCode = it },
        placeholder = { Text(text = "Código Escaneado") },
        singleLine = true,
        //leadingIcon = {
         //   Icon(
          //      imageVector = Icons.Default.Check,
        //        contentDescription = "Icono de Escaneo"
         //   )
        //},
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Campo para escanear código" }
    )
}

@Composable
fun CardItemInformation() {
    var cant by remember { mutableStateOf("") }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = Color(0xFF151635), shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Título del producto
            Text(
                text = "Cereal Integral",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF151635)
                )
            )
            Text(
                text = "Código Barras: 7861024611060",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoText(label = "Saldo", value = "56")
                InfoText(label = "Contado", value = "56")
            }

            OutlinedTextField(
                value = cant,
                onValueChange = { cant = it },
                label = { Text("Ingrese cantidad") },
                placeholder = { Text("Ej: 10") },
                singleLine = true,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),

            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { println("Cantidad agregada: $cant") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151635)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Agregar", color = Color.White, fontSize = 16.sp)
                }

                OutlinedButton(
                    onClick = { cant = "" },
                    border = BorderStroke(1.dp, Color(0xFF7E7D98)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Reiniciar", color = Color(0xFF7E7D98), fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF151635)
            )
        )
    }
}

@Composable
fun CardTableItems() {
    val itemsList = listOf(
        TableItem("CHANCLETA PILSENER 1LT", "25", "02698756"),
        TableItem("GLOBO CARNAVAL BAMBUCO", "20", "02698757"),
        TableItem("PLATO METALICO NAV.H18A2872 (33X1.5CM)", "25", "02698758"),
        TableItem("GLOBO CARNAVAL BAMBUCO", "20", "02698757"),
        TableItem("PLATO METALICO NAV.H18A2872 (33X1.5CM)", "25", "02698758"),
        TableItem("CHANCLETA PILSENER 1LT", "25", "02698756"),
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = Color(0xFF151635), shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Listado Cargado",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF151635)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(itemsList) { item ->
                    TableItemRow(item)
                }
            }
        }
    }
}

data class TableItem(val name: String, val quantity: String, val code: String)

@Composable
fun TableItemRow(item: TableItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF151635)
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = item.quantity,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF151635)
                )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.code,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Gray
            )
        )
        Divider(color = Color(0xFFDADADA), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun SearchDialog(onDismiss: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Buscar Producto") },
        text = {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(text = "Ingrese término de búsqueda") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "Campo de búsqueda" }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            // Simular tiempo de búsqueda
                            delay(1000)
                            searchResults = fetchSearchResults(searchQuery)
                            isLoading = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151635)),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = searchQuery.isNotBlank()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Buscando...", color = Color.White)
                    } else {
                        Text(text = "Buscar", color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (searchResults.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(searchResults) { result ->
                            SearchResultItem(result, onResultSelected = {
                                println("Seleccionado: $it")
                                onDismiss()
                            })
                        }
                    }
                } else {
                    if (!isLoading && searchQuery.isNotBlank()) {
                        Text(
                            text = "No se encontraron resultados.",
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun SearchResultItem(result: String, onResultSelected: (String) -> Unit) {
    TextButton(
        onClick = { onResultSelected(result) },
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Resultado de búsqueda: $result" }
    ) {
        Text(text = result, color = Color(0xFF151635))
    }
}

suspend fun fetchSearchResults(query: String): List<String> {
    // Simula una solicitud fetch
    delay(500) // Simular tiempo de red
    return if (query.isNotBlank()) {
        listOf("Producto 1", "Producto 2", "Producto 3", "Item 2", "Item 1").filter { it.contains(query, ignoreCase = true) }
    } else {
        emptyList()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InventoryScreenPreview() {
    InventarioTheme {
        InventoryScreen(rememberNavController())
    }
}
