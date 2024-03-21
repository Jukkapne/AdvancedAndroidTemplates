package com.example.lessontemplate.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


@Composable
fun ShowStorageImage() {
    var byteArray by remember { mutableStateOf<ByteArray?>(null) }

    var painter = rememberAsyncImagePainter(
        model = byteArray
    )

    Column() {
        Button(onClick = {
            Firebase.storage.reference
                .child("jukkanevalainenoamkfi/profile.jpg")
                .getBytes(1024*1024)
                .addOnSuccessListener {
                    byteArray = it
                }
                .addOnFailureListener {
                    Log.e("***", it.message.toString())
                }
        }) {
            Text(text = "Get image")
        }
        Image(painter = painter , contentDescription = "images")
    }
}

@Composable
fun PickImage() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }



    var launcher = rememberLauncherForActivityResult(


          //Activity contract is a predefined contract for getting content of any type from the device
          //This contract is used to get content from the device. The type of the content is defined in the launch method.
          //The result is returned as a Uri object.
          //When you use this contract, it instructs the system to show a UI that allows the user to pick content.
          //Depending on the MIME type you specify (in this case, "image/*"), it can open a file explorer, gallery, or any appropriate picker that lets the user choose an image.


        contract = ActivityResultContracts.GetContent()){
        imageUri = it
    }
    
    Column() {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Open image")
        }
        Button(onClick = { fileToStorage(imageUri) }) {
            Text(text = "Send image to storage")
        }
    }
}

fun fileToStorage(uri: Uri?){
    var imageRef = Firebase.storage.reference.child("profile.jpg")

    uri?.let{ u ->
        imageRef.putFile(u)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { remoteUri ->
                    Log.d("***", remoteUri.toString())
                }
            }
            .addOnFailureListener{
                Log.e("***", it.message.toString())
            }
    }
}