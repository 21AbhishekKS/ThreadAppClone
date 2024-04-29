package com.abhi.threadsclone.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.abhi.threadsclone.Components.ThreadItem
import com.abhi.threadsclone.Components.UserItem
import com.abhi.threadsclone.viewModels.HomeViewModel
import com.abhi.threadsclone.viewModels.SearchViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun  Search(navHostController: NavHostController){

    val searchViewModel : SearchViewModel = viewModel()
    val userList by searchViewModel.usersList.observeAsState(null)
    val context = LocalContext.current


    var searchName by remember {
        mutableStateOf("")
    }


    Column {

        Text(text = "Search",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp
        )


        OutlinedTextField(value = searchName,
            onValueChange = { searchName = it},
            label = { Text(text = "Search") },
            placeholder = { Text(text = "Search") },
            trailingIcon = { IconButton(onClick = { searchName="" }) {
                Icon(imageVector = Icons.Rounded.Clear, contentDescription = "" )
            }
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
        )



        LazyColumn{

            val filterItems = userList?.filter { it.name!!.contains(searchName, ignoreCase = true) }

            items(filterItems ?: emptyList()) { pairs->

                UserItem(user = pairs,
                    navHostController = navHostController)

            }
        }
    }

}