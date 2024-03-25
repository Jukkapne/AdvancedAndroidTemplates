package com.example.lessontemplate

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lessontemplate.data.User
import com.example.lessontemplate.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AuthExample() {
    val vm: FirebaseViewModel =  viewModel()


    vm.signInUser("jukka.nevalainen@oamk.fi", "F1rebasedemo")
    Column {

    Greeting(name = "Android")
    Button(onClick = { vm.addPersonalMessage("MOI") }) {
    }
    }
}

@Composable
fun AddUserView(viewModel: FirebaseViewModel = viewModel()) {
    var user by remember { mutableStateOf(User("", "", "", 0)) }


    Column {
        OutlinedTextField(value = user.username, onValueChange = {user.username = it})
        OutlinedTextField(value = user.fname, onValueChange = {user.fname = it})
        OutlinedTextField(value = user.lname, onValueChange = {user.lname = it})
        OutlinedTextField(value = user.age.toString(), onValueChange = {user.age = it.toInt()})

        OutlinedButton(onClick = { viewModel.addUser(user) }) {
            Text(text = "Add user")
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
Log.d("getallusers", "getting all users")
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
        .document("k7PgUI2eWg5BdkLSqoe7")
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


