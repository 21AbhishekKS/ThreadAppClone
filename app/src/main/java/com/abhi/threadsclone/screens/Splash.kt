package com.abhi.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.abhi.threadsclone.R
import com.abhi.threadsclone.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun  Splash(navController: NavHostController )
 {

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Image(painter = painterResource(id = R.drawable.threads_logo ), contentDescription = "" )
    }




    LaunchedEffect(true ){
        delay(1000)

        if(FirebaseAuth.getInstance().currentUser != null){
            navController.navigate(Routes.BottomNav.routes)
            {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }
        else
        {
            navController.navigate(Routes.Login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }




    }

}