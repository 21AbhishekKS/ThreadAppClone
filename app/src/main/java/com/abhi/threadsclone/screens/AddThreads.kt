package com.abhi.threadsclone.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.abhi.threadsclone.R
import com.abhi.threadsclone.navigation.Routes
import com.abhi.threadsclone.utils.SharedPref
import com.abhi.threadsclone.viewModels.AddThreadViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.thread

//@Preview(showBackground = true)
@Composable
fun  AddThreads(navHostController: NavHostController){
    val addthreadViewModel : AddThreadViewModel = viewModel()
    val isPosted by addthreadViewModel.isposted.observeAsState()



    var thread by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var threadImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val ThreadImagelauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? ->
        threadImageUri = uri
    }

    LaunchedEffect(key1 = isPosted ){
        if (isPosted == true){
            thread=""
            threadImageUri= null
            Toast.makeText(context,"Thread Added",Toast.LENGTH_SHORT).show()
            navHostController.navigate(Routes.Home.routes){
                popUpTo(Routes.AddThreads.routes){
                    inclusive=true
                }
                launchSingleTop=true
            }

        }
    }

    Column(modifier = Modifier.fillMaxHeight()
        ,verticalArrangement = Arrangement.SpaceBetween) {


    Column(modifier = Modifier.fillMaxHeight(.90f)) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {

                navHostController.navigate(Routes.Home.routes){
                    popUpTo(Routes.AddThreads.routes){
                        inclusive=true
                    }
                    launchSingleTop=true
                }

            }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription ="" )
            }
            Text(text = "New thread", fontSize = 24.sp)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically) {
                Image(painter = //painterResource(id = R.drawable.user),
                rememberAsyncImagePainter(model = SharedPref.getimageUrl(context)),
                    contentDescription = "" ,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale=(ContentScale.Crop)
                )

                Text(text =  SharedPref.getUserName(context),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(10.dp))
        }
        BasicTextFieldWithHint(hint = "Start a new thread" ,
            value = thread ,
            onvalueChange =  { thread = it},
            modifier = Modifier.padding(horizontal = 50.dp))

        IconButton(onClick = {
            ThreadImagelauncher.launch("image/*")

                             },modifier = Modifier.padding(horizontal = 40.dp)) {
            Icon(imageVector = Icons.Rounded.AddCircle, contentDescription ="" )
        }



        Image(
            rememberAsyncImagePainter(model =threadImageUri),
            contentDescription ="",
            Modifier
                .padding(horizontal = 50.dp)
                .size(400.dp)
                .fillMaxWidth()
                .clip(RectangleShape))



    }

    Row(modifier = Modifier
        .padding(horizontal = 5.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = "Anyone can replay",
            modifier = Modifier.padding(10.dp)
            )

        TextButton(onClick =
        {
            if(threadImageUri == null){
                addthreadViewModel.saveData(thread, FirebaseAuth.getInstance().currentUser!!.uid,"")
            }else{
                addthreadViewModel.saveImage(thread,FirebaseAuth.getInstance().currentUser!!.uid,threadImageUri!!)
            }


        },) {
            Text(text = "Post",
                fontWeight = FontWeight.Bold,
                color = Color.Green)
        }

    }
    }
}

@Composable
fun BasicTextFieldWithHint(hint: String,
                           value : String,
                           onvalueChange : (String) -> Unit,
                           modifier: Modifier){

    Box(modifier = modifier){
        if(value.isEmpty()){
            Text(text = hint , color = Color.Gray)
        }
        BasicTextField(value = value,
            onValueChange = onvalueChange,
            modifier = Modifier.fillMaxWidth())
    }

}