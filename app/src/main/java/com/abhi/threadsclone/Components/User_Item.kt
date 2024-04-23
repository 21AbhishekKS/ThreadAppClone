package com.abhi.threadsclone.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.abhi.threadsclone.Models.ThreadModel
import com.abhi.threadsclone.Models.UserModel
import com.abhi.threadsclone.R
import com.abhi.threadsclone.navigation.Routes
import com.abhi.threadsclone.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserItem(
    user: UserModel,
    navHostController: NavHostController
){

    val context = LocalContext.current




        Row(modifier = Modifier
            .clickable {

                if (user.uid == FirebaseAuth.getInstance().currentUser?.uid){
                    navHostController.navigate(Routes.Profile.routes)
                }

                else{
                    val routes = Routes.OtherUSer.routes.replace("{data}",user.uid)
                    navHostController.navigate(routes)
                }

            }
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(painter = //painterResource(id = R.drawable.user),
            rememberAsyncImagePainter(model = user.imageUrl),
                contentDescription = "" ,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale=(ContentScale.Crop)
            )

            Text(text =  user.userName,
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp))

    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}

