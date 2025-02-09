package com.boceto.inventario.screens.search

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boceto.inventario.ui.theme.InventarioTheme
import com.boceto.inventario.navigate.Routes
import com.boceto.inventario.network.ValueItem
import com.boceto.inventario.screens.inventory.InventoryUiState
import com.boceto.inventario.screens.inventory.InventoryViewModel
import com.boceto.inventario.screens.inventory.SeachViewModel


@Composable
fun InventoryScreen(
    navHostController: NavHostController,
    idBodega: String,
    seccion: Int,
    viewModel: InventoryViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.FetchInventoryCounting(idBodega,seccion)
    }
    var isDialogVisible by remember { mutableStateOf(false) }
    var codeSelected by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    when{
        uiState.notFoundProduct -> {
            //TODO: Show dialog not found product
        }
    }

    if (isDialogVisible) {
        SearchDialog(
            idBodega = idBodega,
            seccion= seccion,
            onDismiss = { isDialogVisible = false },
            onSuccessSearch= {code ->
                isDialogVisible= false
                codeSelected = code
                viewModel.getProduct(code,idBodega,seccion)
            }
        )
    }

    Scaffold(
        topBar = {
            InventoryTopAppBar(navHostController, onSearchClick = { isDialogVisible = true })
        },
        content = { paddingValues ->
            InventoryContent(
                modifier = Modifier.padding(paddingValues),
                idBodega,
                uiState,
                seccion,
                codeSelected
            )
        }
    )
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
fun InventoryContent(
    modifier: Modifier = Modifier,
    idBodega: String,
    uiState: InventoryUiState,
    seccion: Int,
    codeSelected: String,
) {
    var isUpdateProduct by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ScanField(
            idBodega,
            seccion,
            codeSelected = codeSelected,
            isUpdateProduct = isUpdateProduct,
            update = { newValue -> isUpdateProduct = newValue }
        )
        if (uiState.value != null) {
            CardItemInformation(uiState.value, idBodega, seccion){
                isUpdateProduct = it
            }
        } else {
            Text("No hay información disponible", color = Color.Gray)
        }
    }
}

@Composable
fun ScanField(
    idBodega: String,
    seccion: Int,
    viewModel: InventoryViewModel = hiltViewModel(),
    codeSelected: String,
    isUpdateProduct: Boolean,
    update: (Boolean) -> Unit
) {
    var scanCode by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Log.d("item", "itemmmmm: $isUpdateProduct")

    LaunchedEffect(isUpdateProduct) {
        if (isUpdateProduct) {
            scanCode = ""
            update(false)
        }
    }

    LaunchedEffect(codeSelected) {
        if (codeSelected.isNotEmpty()) {
            scanCode = codeSelected
            Log.d("item", "Se está leyendo: $scanCode")
        }
    }

    LaunchedEffect(isUpdateProduct) {
       focusRequester.requestFocus()
        Log.d("item", "Se está leyendoooooooo: $isUpdateProduct")
    }

    OutlinedTextField(
        value = scanCode,
        onValueChange = { scanCode = it },
        placeholder = { Text(text = "Código Escaneado") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                if (!state.isFocused) {
                    if (scanCode.isNotEmpty()) {
                        Log.d("Scan", "El escáner inicia: $scanCode")
                        viewModel.getProduct(scanCode, idBodega, seccion)
                    }
                    focusManager.moveFocus(FocusDirection.Next)
                }
            },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun CardItemInformation(
    valueItem: ValueItem,
    idBodega: String,
    seccion: Int,
    viewModel: InventoryViewModel = hiltViewModel(),
    isUpdateProduct: (Boolean) -> Unit
) {
    var cant by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

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
                text = valueItem.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF151635)
                )
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoText(label = "Stock: ", value =valueItem.saldo.toString())
                InfoText(label = "Sección: ", value =valueItem.totalSeccion.toString())
                InfoText(label = "General: ", value =valueItem.totalGeneral.toString())
            }

            OutlinedTextField(
                value = cant,
                onValueChange = { cant = it },
                label = { Text("Ingrese cantidad") },
                placeholder = { Text("Ej: 10") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { (it.isFocused) }
                    .focusable()
                ,
                shape = RoundedCornerShape(8.dp),
                )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.sendItem(
                            idBodega = idBodega,
                            idSeccion = seccion,
                            idItem = valueItem.code,
                            cantidad = cant.toDouble(),
                            saldo = valueItem.saldo
                        )
                        isUpdateProduct(true)
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151635)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Agregar",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                OutlinedButton(
                    onClick = {
                        viewModel.updateItem(
                            idBodega = idBodega,
                            idSeccion = seccion,
                            idItem = valueItem.code,
                            cantidad = cant.toDouble(),
                            saldo = valueItem.saldo
                        )
                        isUpdateProduct(true)
                    },
                    border = BorderStroke(1.dp, Color(0xFF7E7D98)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Reingresar",
                        color = Color(0xFF7E7D98),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Row {
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
fun SearchDialog(
    idBodega: String,
    seccion: Int,
    onDismiss: () -> Unit,
    viewModel: SeachViewModel = hiltViewModel(),
    onSuccessSearch:(String) -> Unit
) {
    // Obtenemos el estado actual del ViewModel usando collectAsState
    val uiState = viewModel.uiState.collectAsState().value

    var searchQuery by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Buscar Producto") },
        text = {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(text = "Ingrese término de búsqueda") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.getProducts(searchQuery, idBodega, seccion)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF151635)),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Buscar", color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Indicador de carga
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                // Mostrar resultados o mensaje de error
                when {
                    uiState.notFoundProduct -> {
                        Text(
                            text = uiState.messageNotFoundProduct,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    uiState.value.isNotEmpty() -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(uiState.value) { item ->
                                Text(
                                    text = item.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .drawBehind {
                                            drawLine(
                                                color = Color(0xFFA6AEBF),
                                                start = Offset(0f, size.height),
                                                end = Offset(size.width, size.height),
                                                strokeWidth = 1.dp.toPx()
                                            )
                                        }
                                        .clickable {
                                            onSuccessSearch(item.codeBars)
                                            Log.d("Scan", "item.code inicia: ${item.codeBars}")
                                        }
                                        .padding(16.dp),
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    else -> {
                        Text(
                            text = "No se encontraron resultados",
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    InventarioTheme {
        InventoryScreen(rememberNavController(), idBodega = "03", seccion = 3)
    }
}
