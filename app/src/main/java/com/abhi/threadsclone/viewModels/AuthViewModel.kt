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
import com.abhi.threadsclone.Models.UserModel
import com.abhi.threadsclone.navigation.Routes
import com.abhi.threadsclone.utils.SharedPref
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.time.delay
import java.util.UUID

class AuthViewModel : ViewModel() {
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
   private val db : FirebaseDatabase = FirebaseDatabase.getInstance()

    val userRef : DatabaseReference = db.getReference("users")




    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context){

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)



                    getData(auth.currentUser!!.uid,context)

                }else{
                    _error.postValue("unable to login")
                    Toast.makeText(context,it.exception.toString(),Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun getData(uid: String, context: Context) {


        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val userData  = snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(userData!!.name, userData!!.email, userData!!.bio, userData!!.userName, userData!!.imageUrl,context)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Cancelled get data",Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun register(email : String ,
                 password : String ,
                 name : String ,
                 bio : String ,
                 userName : String,
                 imageUri : Uri,
                 context: Context){

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(email,password,name,bio,userName,imageUri,auth.currentUser?.uid,context)

                }else{
                    _error.postValue("unable to login")
                    Toast.makeText(context,it.exception.toString(),Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveImage(email: String, password: String, name: String, bio: String, userName: String, imageUri : Uri, uid: String?,context:Context) {

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {

            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email,password,name,bio,userName,it.toString(),uid,context)
            }
        }
    }

    private fun saveData(email: String,
                         password: String,
                         name: String,
                         bio: String,
                         userName: String,
                         imageUrl: String,
                         uid: String?,
                         context: Context) {

        val fireStoreDb = Firebase.firestore
        val followersRef = fireStoreDb.collection("followers").document(uid!!)
        val followingRef = fireStoreDb.collection("following").document(uid)


        followingRef.set(mapOf("followingIds" to listOf<String>()))
        followersRef.set(mapOf("followersIds" to listOf<String>()))


        val userData = UserModel(password, email, name, bio, userName, imageUrl,uid!!)

        userRef.child(uid).setValue(userData).addOnSuccessListener {
            SharedPref.storeData(name,email,bio,userName,imageUrl,context)

        }.addOnFailureListener{
            Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()

        }

    }

    fun logOut(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}