package com.example.lessontemplate

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class FirebaseAuthViewModel: ViewModel() {

    var user = mutableStateOf<FirebaseUser?>(null);

    fun registerUser(email: String, pw: String ){
        viewModelScope.launch {
            Firebase.auth
                .createUserWithEmailAndPassword(email, pw)
                .addOnSuccessListener {
                    user.value = it.user
                    Log.d("******", "Registering done!!")
                }
                .addOnFailureListener {
                    Log.e("******", it.message.toString())
                }
        }

    }

    fun signInUser(email: String, pw: String ){
        viewModelScope.launch {
            Firebase.auth.signInWithEmailAndPassword(email, pw)
                .addOnSuccessListener {
                    user.value = it.user
                    Log.d("******", "Sign in done!!")
                }
                .addOnFailureListener {
                    Log.e("******", it.message.toString())
                }
        }
    }

    //Made an update here. The question mark was missing and that's why
    //the fUser was nullable.
    fun addPersonalMessage(msg: String){
        viewModelScope.launch {
            user.value?.let { fUser ->
                Firebase.firestore.collection("udata")
                    .document(fUser.uid)
                    .set(mapOf("msg" to msg))
                    .addOnSuccessListener {
                        Log.d("******", "Sign in done!!")
                    }
                    .addOnFailureListener {
                        Log.e("******", it.message.toString())
                    }
            }

        }
    }

    fun logout(){
        viewModelScope.launch {
            Firebase.auth.signOut()
            user.value = null;
        }
    }
}