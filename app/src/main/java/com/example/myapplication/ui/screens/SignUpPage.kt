package com.example.myapplication.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.myapplication.ui.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope


@Composable
fun SignUpPage(navController: NavHostController, userViewModel: UserViewModel, coroutineScope: CoroutineScope){

    SignUp(navController,userViewModel,coroutineScope)
}