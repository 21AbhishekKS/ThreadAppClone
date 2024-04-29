package com.abhi.threadsclone.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi.threadsclone.screens.AddThreads
import com.abhi.threadsclone.screens.BottomNav
import com.abhi.threadsclone.screens.Home
import com.abhi.threadsclone.screens.Login
import com.abhi.threadsclone.screens.Notification
import com.abhi.threadsclone.screens.OtherUser
import com.abhi.threadsclone.screens.Profile
import com.abhi.threadsclone.screens.Register
import com.abhi.threadsclone.screens.Search
import com.abhi.threadsclone.screens.Splash

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun  NavGraph(navController: NavHostController){
NavHost(navController = navController, startDestination = Routes.Splash.routes ){

    composable(Routes.Splash.routes){
        Splash(navController)
    }
    composable(Routes.Home.routes){
        Home(navController)
    }

    composable(Routes.Notification.routes){
        Notification()
    }
    composable(Routes.Profile.routes){
        Profile(navController)
    }
    composable(Routes.Search.routes){
        Search(navController)
    }
    composable(Routes.AddThreads.routes){
        AddThreads(navController)
    }
    composable(Routes.BottomNav.routes){
        BottomNav(navController)
    }
    composable(Routes.Login.routes){
        Login(navController)
    }
    composable(Routes.Register.routes){
        Register(navController)
    }
    composable(Routes.OtherUSer.routes){
        val data = it.arguments!!.getString("data")
        OtherUser(navController,data!!)
    }
}
}