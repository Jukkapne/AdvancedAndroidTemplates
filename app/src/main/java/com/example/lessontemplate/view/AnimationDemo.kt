package com.example.lessontemplate.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun AnimAppear() {

    var x by remember { mutableStateOf(false) }

    Column() {
        Button(onClick = { x = !x }) {
            Text(text = "OK")
        }
        
        AnimatedVisibility(
            visible = x,
            enter =  fadeIn(
                animationSpec = repeatable(iterations = 3, animation = tween(durationMillis = 2000))
            ),
            exit = slideOutHorizontally(
                animationSpec = tween(durationMillis = 3000, easing = LinearEasing ),
                targetOffsetX = {y -> y}
            )
        ) {
            Box(modifier = Modifier
                .size(200.dp)
                .background(Color.Red))
        }
        
    }
}

@Composable
fun AnimValues() {
    var x by remember { mutableStateOf(false) }
    var c by remember { mutableStateOf(Color.Green) }


    val mycolor by animateColorAsState(
        targetValue = if(x) Color.Red else Color.Green,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing )
    ){
        //c = Color.Red
    }

    Column {
        Button(onClick = { x = !x }) {
            Text(text = "OK")
        }
        Box(modifier = Modifier
            .size(200.dp)
            .background(mycolor))
    }
}

@Composable
fun AnimInf() {
    val transition = rememberInfiniteTransition()

    val opacity by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f ,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column() {
        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red.copy(alpha = opacity ))
        ) {
            Text(text = "SUBMIT")
        }
    }
    
}

