package com.boceto.inventario.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.boceto.inventario.Manifest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit
){
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted){
        //El permiso fue permitido
    } else {

        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}