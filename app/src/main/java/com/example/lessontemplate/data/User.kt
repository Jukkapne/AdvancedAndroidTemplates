package com.example.lessontemplate.data

import com.google.firebase.firestore.Exclude

data class User(@get:Exclude var username: String, var fname: String, var lname: String, var age: Int)
