package com.abhi.threadsclone.screens

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

import com.abhi.threadsclone.R
import com.abhi.threadsclone.navigation.Routes
import com.abhi.threadsclone.viewModels.AuthViewModel

//@Preview(showBackground = true)
@Composable
fun  Register(navController : NavHostController)
{
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var userName by remember {
        mutableStateOf("")
    }

    var bio by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        uri: Uri? ->
        imageUri = uri
    }

    val authViewModel : AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)


    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),){
        isgranted : Boolean ->
        if(isgranted){

        }else{

        }
    }


    val context = LocalContext.current

    val permissionLaunchers = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),){
            isgranted : Boolean ->

    }


    var pemissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        android.Manifest.permission.READ_MEDIA_IMAGES
    }else{
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }



    LaunchedEffect(firebaseUser){
        if (firebaseUser!= null){
            navController.navigate(Routes.BottomNav.routes)
        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Image(painter = if (imageUri == null) painterResource(id = R.drawable.user)
            else rememberAsyncImagePainter(model = imageUri),
            contentDescription = "",
            Modifier
                .size(120.dp)
                .clickable {

                    val isGrandted = ContextCompat.checkSelfPermission(
                        context, pemissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGrandted) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(pemissionToRequest)
                    }

                }
                .clip(CircleShape))


        Spacer(Modifier.size(25.dp))


        Text(text = "Register",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp
        )



        OutlinedTextField(value = name,
            onValueChange = { name = it},
            label = { Text(text = "Name") },
            placeholder = { Text(text = "Enter Name") },
            trailingIcon = { IconButton(onClick = { name="" }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
            }
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        )


        OutlinedTextField(value = userName,
            onValueChange = { userName = it},
            label = { Text(text = "UserName") },
            placeholder = { Text(text = "Enter UserName") },
            trailingIcon = { IconButton(onClick = { userName="" }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
            }
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .padding(vertical = 5.dp)
        )

        OutlinedTextField(value = bio,
            onValueChange = { bio = it},
            label = { Text(text = "Bio") },
            placeholder = { Text(text = "Enter Bio") },
            trailingIcon = { IconButton(onClick = { bio="" }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
            }
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .padding(vertical = 5.dp)
        )


        OutlinedTextField(value = email,
            onValueChange = { email = it},
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Enter Email") },
            trailingIcon = { IconButton(onClick = { email="" }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
            }
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .padding(vertical = 5.dp)
        )

        OutlinedTextField(value = password,
            onValueChange = { password = it},
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter Password") },
            trailingIcon = { IconButton(onClick = { password="" }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
            }
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .padding(vertical = 5.dp)
        )

        ElevatedButton(onClick = {

            if (name.isEmpty() || email.isEmpty() || bio.isEmpty() || password.isEmpty() || imageUri== null){
                 Toast.makeText(context,"Please fill all information",Toast.LENGTH_SHORT).show()
            }
            else{
                authViewModel.register(email,password,name,bio,userName, imageUri!!,context)


            }
         },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(20.dp)) {
            Text(text = "Register",
                fontSize = 20.sp)
        }

        TextButton(onClick = {
            navController.navigate(Routes.Login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true }

        } ) {
            Text(text = "Already registered ? Login Here")
        }
    }
}