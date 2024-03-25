package com.example.lessontemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.lessontemplate.ui.theme.LessonTemplateTheme
import com.example.lessontemplate.view.AnimAppear
import com.example.lessontemplate.view.CameraPermissionsView
import com.example.lessontemplate.view.GameView
import com.example.lessontemplate.view.PickImage
import com.example.lessontemplate.view.ShowStorageImage
import com.example.lessontemplate.view.SubmitGameForm

import com.example.lessontemplate.viewmodel.FirebaseViewModel
import com.example.lessontemplate.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = FirebaseViewModel()
        val gamevievModel = GameViewModel()
        setContent {
            LessonTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //CameraPermissionsView()
                    //MainAuthView(viewModel)
                    //AuthExample()
                    //GameView()
                    //ShowStorageImage()
                    //PickImage()
                    //AnimAppear()
                    //AnimValues()
                    //AnimInf()
                    //SubmitGameForm(gamevievModel)
                    AddUserView(viewModel)


                }
            }
        }
    }
}
