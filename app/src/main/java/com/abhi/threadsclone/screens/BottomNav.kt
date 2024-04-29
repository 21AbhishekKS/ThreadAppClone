package com.abhi.threadsclone.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhi.threadsclone.Models.BottomNavItem
import com.abhi.threadsclone.navigation.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun  BottomNav(navController: NavHostController){

    val nav1Controller : NavHostController = rememberNavController()

    Scaffold(bottomBar = { MyBottomBar(nav1Controller) }) { innerPadding ->

        NavHost(navController = nav1Controller, startDestination = Routes.Home.routes,
           modifier =  Modifier.padding(innerPadding)){

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
                AddThreads(nav1Controller)
            }
            composable(Routes.BottomNav.routes){
                BottomNav(navController)
            }
        }


    }
}

@Composable
fun MyBottomBar(nav1Controller: NavHostController){

    val backStackEntry = nav1Controller.currentBackStackEntryAsState()

    val list = listOf<BottomNavItem>(
        BottomNavItem(
            "Home",
            Routes.Home.routes,
            Icons.Default.Home
        ) ,
        BottomNavItem(
            "Search",
            Routes.Search.routes,
            Icons.Default.Search
        ),
        BottomNavItem(
            "Add Threads",
            Routes.AddThreads.routes,
            Icons.Default.Add
        ),
        BottomNavItem(
            "Notification",
            Routes.Notification.routes,
            Icons.Default.Notifications
        ),
        BottomNavItem(
            "profile",
            Routes.Profile.routes,
            Icons.Default.Person
        )
    )

    BottomAppBar {
        list.forEach {
            val selected = it.routes == backStackEntry?.value?.destination?.route
            NavigationBarItem(selected = selected,
                onClick = { nav1Controller.navigate(it.routes){
                    popUpTo(nav1Controller.graph.findStartDestination().id){
                        saveState = true
                    }
                } },
                icon = {
                    Icon(
                imageVector = it.icon,
                contentDescription = it.title
                    )
                })
        }
    }
    
}
