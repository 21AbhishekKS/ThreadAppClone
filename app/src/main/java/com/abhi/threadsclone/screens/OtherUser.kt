package com.abhi.threadsclone.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.abhi.threadsclone.Components.ThreadItem
import com.abhi.threadsclone.Models.UserModel
import com.abhi.threadsclone.utils.SharedPref
import com.abhi.threadsclone.viewModels.AuthViewModel
import com.abhi.threadsclone.viewModels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OtherUser(navController: NavHostController , uid : String) {


    val context = LocalContext.current
    val authViewModel : AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val profileViewModel : ProfileViewModel = viewModel()
    val threads by profileViewModel.threads.observeAsState(null)
    val user by profileViewModel.users.observeAsState(null)
    val followersList by profileViewModel.followerList.observeAsState(null)
    val followingList by profileViewModel.followingList.observeAsState(null)

   // val user = UserModel(
    //        name = SharedPref.getName(context),
    //        imageUrl = SharedPref.getimageUrl(context),
    //        userName = SharedPref.getUserName(context),
    //        bio = SharedPref.getbio(context)
    //
    //    )

    profileViewModel.fetchThread(uid)
    profileViewModel.fetchUser(uid)
    profileViewModel.getFollowers(uid)
    profileViewModel.getFollowing(uid)


    var currentUserId = ""
    if(FirebaseAuth.getInstance().currentUser != null)
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid


//    LaunchedEffect(firebaseUser){
//        if (firebaseUser == null){
//            navController.navigate(Routes.Login.routes){
//                popUpTo(navController.graph.startDestinationId)
//                launchSingleTop=true }
//        }
//    }


    //  Text(text = "Profile", modifier = Modifier.clickable {
    //        authViewModel.logOut()
    //    })

    Column() {
        Text(text = "Profile",
            fontWeight = FontWeight.Bold,
            fontSize =26.sp,
            modifier = Modifier.padding(5.dp))

        Divider(color = Color.DarkGray, thickness = 1.5.dp)


        Row(modifier = Modifier
            .padding(top = 5.dp)
            .padding(horizontal = 25.dp) ,
            verticalAlignment = Alignment.CenterVertically) {

            Column(
                Modifier.fillMaxWidth(.65f),
            ){
                user?.let {
                    Text(text = it.userName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp)
                }

                user?.let {
                    Text(text = it.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,modifier = Modifier.padding(top = 5.dp))
                }
            }

            Image(painter = //painterResource(id = R.drawable.user),
            rememberAsyncImagePainter(model = user?.imageUrl),
                contentDescription = "" ,

                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale=(ContentScale.Crop)
            )
        }

        user?.let {
            Text(text = it.bio,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(horizontal = 25.dp))
        }

        Row {
            Text(text = "${followersList?.size} Followers", fontWeight = FontWeight.Normal, fontSize = 13.sp
                , modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 25.dp))


            Text(text = "${followingList?.size} Following", fontWeight = FontWeight.Normal, fontSize = 13.sp
                , modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 25.dp))
        }


        ElevatedButton(onClick ={
            if(currentUserId !="")
                    profileViewModel.followUser(uid,currentUserId)
            },
            Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 25.dp)
        ) {

            Text(text = if (followersList!= null && followersList!!.isNotEmpty() && followersList!!.contains(currentUserId)) "Following" else "Follow")

        }



    if (threads != null && user != null){


    LazyRow(){
        items(threads ?: emptyList()){pair->

            ThreadItem(thread = pair, user = user!!, navHostController = navController , useId = SharedPref.getUserName(context))
        }

    }
    }

    }
}