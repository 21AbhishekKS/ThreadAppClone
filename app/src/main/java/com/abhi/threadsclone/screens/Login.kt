package com.abhi.threadsclone.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.abhi.threadsclone.R
import com.abhi.threadsclone.navigation.Routes
import com.abhi.threadsclone.viewModels.AuthViewModel
import com.google.firebase.auth.FirebaseUser

@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
@Composable
fun  Login( navController: NavController)
 {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

     val authViewModel : AuthViewModel = viewModel()

     val firebaseUser : FirebaseUser? by authViewModel.firebaseUser.observeAsState()

     val context = LocalContext.current






     LaunchedEffect(firebaseUser){
         if (firebaseUser != null){

             Toast.makeText(context,"Login Sucessfull",Toast.LENGTH_SHORT).show()

               navController.navigate(Routes.BottomNav.routes){
                            popUpTo(navController.graph.startDestinationId)
                              launchSingleTop=true }

         }
     }

Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {

    Image(painter = painterResource(id = R.drawable.threads_logo) ,
        contentDescription = "", Modifier.size(150.dp) )


    Spacer(Modifier.size(25.dp))


    Text(text = "Login",
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp
    )



    OutlinedTextField(value = email,
        onValueChange = { email = it},
        label = { Text(text = "Email") },
        placeholder = { Text(text = "Enter Email") },
        trailingIcon = { IconButton(onClick = { email="" }) {
            Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
        }}, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ), singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
        )


    OutlinedTextField(value = password,
        onValueChange = { password = it},
        label = { Text(text = "Password") },
        placeholder = { Text(text = "Enter Password") },
        trailingIcon = { IconButton(onClick = { password="" }) {
            Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
        }}, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ), singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    )

    ElevatedButton(onClick = {
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(context,"Fill all fields",Toast.LENGTH_SHORT).show()
        }else{
            authViewModel.login(email,password,context)
        }
                             },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .padding(20.dp)) {
        Text(text = "Login",
            fontSize = 20.sp)
    }

    TextButton(onClick = {  navController.navigate(Routes.Register.routes){
        popUpTo(navController.graph.startDestinationId)
        launchSingleTop=true
    }
    } ) {
        Text(text = "New user? Register")
    }
}

}