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

class HomeViewModel : ViewModel() {

   private val db : FirebaseDatabase = FirebaseDatabase.getInstance()

    val thread : DatabaseReference = db.getReference("thread")


    private var _ThreadAndUser = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val threadAndUser : LiveData<List<Pair<ThreadModel,UserModel>>> = _ThreadAndUser

    init {
        fetchThreadsAndUser {
            _ThreadAndUser.value=it
        }
    }

    private fun fetchThreadsAndUser( onresult : (List<Pair<ThreadModel,UserModel>>)-> Unit){

        thread.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<ThreadModel,UserModel>>()
                for(threadSnapShot in snapshot.children){
                    val thred = threadSnapShot.getValue(ThreadModel :: class.java)
                    thred.let {
                        fetchUserFromThread(it!!){
                             user ->
                            result.add(0,it to user)
                            if (result.size == snapshot.childrenCount.toInt()){
                                onresult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    fun fetchUserFromThread(thread : ThreadModel , onresult:(UserModel)-> Unit){
            db.getReference("users").child(thread.userId)
                .addListenerForSingleValueEvent(object  : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(UserModel :: class.java)
                        user?.let(onresult)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
    }







}