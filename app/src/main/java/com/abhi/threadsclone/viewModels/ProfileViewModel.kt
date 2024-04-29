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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.time.delay
import java.util.UUID

class ProfileViewModel : ViewModel() {

    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()

    val threadRef : DatabaseReference = db.getReference("thread")
    val userRef : DatabaseReference = db.getReference("users")

    private var _threads = MutableLiveData(listOf<ThreadModel>())
    val threads : LiveData<List<ThreadModel>> get()  = _threads

    private var _users = MutableLiveData(UserModel())
    val users : LiveData<UserModel> get()  = _users

    private var _followerList = MutableLiveData(listOf<String>())
    val followerList : LiveData<List<String>> get()  = _followerList

    private var _followingList = MutableLiveData(listOf<String>())
    val followingList : LiveData<List<String>> get()  = _followingList


    fun fetchUser(uid :String){

        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel :: class.java)
                _users.postValue(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchThread(uid :String){

        threadRef.orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val threadList = snapshot.children.mapNotNull {
                    it.getValue(ThreadModel :: class.java)
                }
                _threads.postValue(threadList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    val fireStoreDb = Firebase.firestore

    fun followUser(userid : String , currentUserUid : String){

        //current user is me create reference to following of my account and followers of other selected account

        val followingRef = fireStoreDb.collection("following").document(currentUserUid)
        val followrsRef = fireStoreDb.collection("followers").document(userid)

        //for my following list add other selected user id
        followingRef.update("followingIds",FieldValue.arrayUnion(userid))

        //for his follower list add my user id(current user id)
        followrsRef.update("followersIds",FieldValue.arrayUnion(currentUserUid))

    }

    fun getFollowers(userid : String){
        fireStoreDb.collection("followers").document(userid)
            .addSnapshotListener { value, error ->
                val followerList = value?.get("followersIds") as? List<String> ?: listOf()
                _followerList.postValue(followerList)

            }

    }


    fun getFollowing(userid : String){
        fireStoreDb.collection("following").document(userid)
            .addSnapshotListener { value, error ->
                val followerList = value?.get("followingIds") as? List<String> ?: listOf()
                _followingList.postValue(followerList)

            }
    }



}