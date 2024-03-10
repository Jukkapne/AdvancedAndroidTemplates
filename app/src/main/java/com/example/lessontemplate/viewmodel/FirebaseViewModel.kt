package com.example.lessontemplate.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch

class FirebaseViewModel: ViewModel() {

    //Mutable states for user, personal message and profile image
    var user = mutableStateOf<FirebaseUser?>(null)
    var msg = mutableStateOf<String>("")
    var profileImageUrl = mutableStateOf<Uri?>(null)

    /**
     * Registers user and sends the profile image from the local image uri.
     */
    fun registerUser(email: String, pw: String, profileImageUri: Uri? ){
        viewModelScope.launch {
            Firebase.auth
                .createUserWithEmailAndPassword(email, pw)
                .addOnSuccessListener {
                    user.value = it.user
                    Log.d("******", "Registering done!!")
                    sendProfileImage(profileImageUri)
                }
                .addOnFailureListener {
                    Log.e("******", it.message.toString())
                }
        }

    }

    /**
     * Signs in the user and fetches the profile image from the firebase storage
     */
    fun signInUser(email: String, pw: String ){
        Log.d("login", "signing in"+email+pw)
        viewModelScope.launch {

            Firebase.auth.signInWithEmailAndPassword(email, pw)
                .addOnSuccessListener {
                    user.value = it.user
                    getProfileImage()
                    Log.d("******", "Sign in done!!")
                }
                .addOnFailureListener {
                    Log.e("******", it.message.toString())
                }
        }
    }

    /**
     * Sends personal message to the firestore by using the user id as document id.
     * This way the user data may be authorized for user only.
     */
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

    /**
     * Retrieves the personal message of the user from firestore.
     */
    fun getPersonalMessage(){

        viewModelScope.launch {
            user.value?.let {
                Firebase.firestore.collection("udata")
                    .document(it.uid)
                    .get()
                    .addOnSuccessListener { doc ->
                        msg.value = doc.get("msg").toString()
                    }
                    .addOnFailureListener { ex ->
                        Log.e("******", ex.message.toString())
                    }
            }
        }
    }

    /**
     * Logs out the user and resets the user
     */
    fun logout(){
        viewModelScope.launch {
            Firebase.auth.signOut()
            user.value = null;
        }
    }


    /**
     * Sends profile image to the firebase storage using user email as file name without dot.
     * After successful upload, the remote url is retrieved and saved to state.
     */
    fun sendProfileImage(fileUri: Uri?){
        user.value?.let{ fUser ->
            fileUri?.let{ fUri ->
                viewModelScope.launch {
                    var imageRef =
                        Firebase.storage.reference.child(fUser.email.toString().replace(".", ""))

                    imageRef.putFile(fUri)
                        .addOnSuccessListener {
                            Log.d("***************", "Profile image added/updated")
                            getProfileImage()
                        }
                        .addOnFailureListener {
                            Log.e("***", it.message.toString())
                        }
                }
            }
        }
    }

    /**
     * Gets the user profile image url and saves it to state
     */
    fun getProfileImage(){
        user.value?.let {fUser ->
            Firebase.storage.reference
                .child(fUser.email.toString().replace(".", ""))
                .downloadUrl
                .addOnSuccessListener {
                    Log.d("---------------", it.toString())
                    profileImageUrl.value = it
                }
        }
    }

    /**
     * Listens to document changes in blogs collection.
     * We get notification every time a new document is added or existing modified
     */
    fun listenToMsgChanges(){
        viewModelScope.launch {
            Firebase.firestore.collection("blogs")
                .addSnapshotListener { snapshot, error ->
                    if(error != null){
                        Log.e("--", error.message.toString())
                    }else{
                        snapshot?.let{
                            snapshot.documentChanges.forEach { doc ->
                                Log.d("----", doc.document.get("msg").toString())
                            }
                        }
                    }
                }

        }
    }
}