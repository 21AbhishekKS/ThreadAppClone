package com.abhi.threadsclone.viewModels

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.abhi.threadsclone.Models.ThreadModel
import com.abhi.threadsclone.Models.UserModel
import com.abhi.threadsclone.navigation.Routes
import com.abhi.threadsclone.utils.SharedPref
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import kotlinx.coroutines.time.delay
import java.util.UUID

class AddThreadViewModel : ViewModel() {

   private val db : FirebaseDatabase = FirebaseDatabase.getInstance()

    val userRef : DatabaseReference = db.getReference("thread")



    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("thread/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser> = _firebaseUser

    private val _isposted = MutableLiveData<Boolean>()
    val isposted : LiveData<Boolean> = _isposted







     fun saveImage(thread: String,
                          userId :String,
                           imageUri : Uri) {

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {

            imageRef.downloadUrl.addOnSuccessListener {
                saveData(thread,userId,it.toString())
            }
        }
    }

     fun saveData(thread: String,
                         userId :String,
                         imageUrl : String,
                         ) {

        val ThreadData = ThreadModel(thread, userId, imageUrl,System.currentTimeMillis().toString())

        userRef.child(userRef.push().key!!).setValue(ThreadData).addOnSuccessListener {
            _isposted.postValue(true)
        }.addOnFailureListener{
            _isposted.postValue(false)

        }

    }




}