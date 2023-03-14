package com.example.lessontemplate

import android.util.Log
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
            it.get("").toString()
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

