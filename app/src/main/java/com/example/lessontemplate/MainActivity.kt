package com.example.lessontemplate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lessontemplate.ui.theme.LessonTemplateTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LessonTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FirstExercise()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    OutlinedButton(onClick = { GetAllUsers() }) {
        Text(text = "Add user")
    }
}

fun GetAllUsers(){
    var fireStore = Firebase.firestore;

    fireStore.collection("users")
        .get()
        .addOnSuccessListener {
            it.documents.forEach { doc ->
                Log.d( "---------", doc.get("lname").toString() )
            }
        }
}


fun GetUser(){
    var fireStore = Firebase.firestore;

    fireStore.collection("users")
        .document("repe")
        .get()
        .addOnSuccessListener {
            val name = it.get("fname").toString()
            val lastName: String = it.get("lname").toString();
            val age =  it.get("age").toString().toInt()
            val username = it.id

            val user = User(username, name, lastName, age)

            Log.d("----------", "$name $lastName")
        }
}

fun AddUser(){
    var fireStore = Firebase.firestore;

    /*val user = hashMapOf<String, Any>(
        "fname" to "Matt",
        "lname" to "Damon",
        "age" to 34
    )*/

    val user = User("timoth","Timothy", "Dalton", 45)


    fireStore.collection("users")
        .document(user.username)
        .set(user)
        .addOnSuccessListener {
            Log.d("******", "Adding document was success!!")
        }
        .addOnFailureListener{
            Log.e("******", it.message.toString())
        }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LessonTemplateTheme {
        Greeting("Android")
    }
}