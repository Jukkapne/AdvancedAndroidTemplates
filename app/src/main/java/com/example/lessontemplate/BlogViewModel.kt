package com.example.lessontemplate

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class BlogViewModel: ViewModel() {
    var blogs = mutableStateListOf<String>()
        private set

    fun addBlog(username: String, msg: String){
        viewModelScope.launch {
            Firebase.firestore.collection("blogs")
                .document(username)
                .set(hashMapOf( "msg" to msg ))
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
        }
    }

    fun getBlogs(){
        viewModelScope.launch {
            Firebase.firestore.collection("blogs")
                .get()
                .addOnSuccessListener {
                    val messages = mutableListOf<String>()
                    it.documents.forEach { doc ->
                        messages.add(doc.get("msg").toString())
                    }
                    blogs.clear();
                    blogs.addAll(messages)
                }
                .addOnFailureListener {  }
        }
    }
}