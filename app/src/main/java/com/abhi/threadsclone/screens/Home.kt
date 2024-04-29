package com.abhi.threadsclone.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abhi.threadsclone.Components.ThreadItem
import com.abhi.threadsclone.viewModels.AuthViewModel
import com.abhi.threadsclone.viewModels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun  Home(navHostController: NavHostController){
    val homeViewModel : HomeViewModel = viewModel()
    val threadAndUsers by homeViewModel.threadAndUser.observeAsState(null)
    val context = LocalContext.current


    LazyColumn{
        items(threadAndUsers ?: emptyList()) {pairs->
            FirebaseAuth.getInstance().currentUser?.let { ThreadItem(thread=pairs.first, user = pairs.second , navHostController, it.uid) }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//
//fun ShowHome(){
//    Home()
//}
