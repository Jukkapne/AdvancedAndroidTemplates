package com.example.lessontemplate.view

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionsView() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    when(permissionState.status){
        is PermissionStatus.Granted -> {
            CameraMainView()
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

@Composable
fun CameraMainView() {

    var lensF = CameraSelector.LENS_FACING_FRONT
    var lensB = CameraSelector.LENS_FACING_BACK


    var imageUri: Uri? by remember{ mutableStateOf(null) }
    var lensFacing: Int by remember { mutableStateOf(lensB) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }

    val camSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    if(imageUri == null){
        SideEffect {
            ProcessCameraProvider.getInstance(context).apply {
                addListener({
                    val camProvider = get()
                    val preview = Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    camProvider.unbindAll()
                    camProvider.bindToLifecycle(lifecycleOwner, camSelector, preview, imageCapture  )
                }, ContextCompat.getMainExecutor(context))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        if(imageUri == null){
            CameraPreview(previewView, {
                lensFacing = if(lensFacing == lensB ) lensF else lensB
            }) {
                takePhoto(context, imageCapture) { imageUri = it }
            }
        }else{
            ShowImageView(imageUri){imageUri = null}
        }
    }
}

@Composable
fun CameraPreview(
    previewView: PreviewView,
    changeLens: () -> Unit,
    takePhoto: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
        Column() {
            Icon(
                Icons.Sharp.CheckCircle,
                "takePhoto",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { takePhoto() },
                tint = Color.White
            )
            Icon(
                Icons.Sharp.ArrowForward,
                "changeLens",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { changeLens() },
                tint = Color.White
            )
        }
    }
}

@Composable
fun ShowImageView(imageUri: Uri?, takeImage: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUri) ,
            contentDescription = "image",
            modifier = Modifier.border(2.dp, Color.Black)
        )
        Button(onClick = { takeImage() }) {
            Text(text = "Take another photo")
        }
    }
}

fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (Uri?) -> Unit
){
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }

    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()

    imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object: ImageCapture.OnImageSavedCallback{
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            Log.d("--<<<<<<<<<<<<<<<<<<<<----", outputFileResults.savedUri.toString() )
            onImageCaptured(outputFileResults.savedUri)
        }

        override fun onError(exception: ImageCaptureException) {
           Log.e("", "Take photo error", exception)
        }
    })
}