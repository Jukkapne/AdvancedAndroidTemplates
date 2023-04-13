package com.example.lessontemplate.view

import android.Manifest
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainCameraView() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    when(permissionState.status){
        is PermissionStatus.Granted -> {
            Text(text = "Permission to camera granted")
        }
        else -> {
            if (permissionState.status.shouldShowRationale) {
                //Denied once and should show the dialog again
                Text(text = "You should really give permission to use this feature")

            } else {
                //When first time starting the application and if permission denied twice (or in setting)
                Text(text = "You've denied permission for camera")
            }
            SideEffect {
                permissionState.launchPermissionRequest()
            }
        }
    }
}

