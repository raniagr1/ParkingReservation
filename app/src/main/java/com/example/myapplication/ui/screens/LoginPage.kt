package com.example.myapplication.ui.screens

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.myapplicationTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.myapplication.Destination
import com.example.myapplication.data.model.User
import com.example.myapplication.ui.viewmodels.UserViewModel
import com.example.trying.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch // Add this import
import kotlinx.coroutines.withContext
import androidx.compose.runtime.collectAsState
import com.example.myapplication.RC_SIGN_IN
import com.example.myapplication.data.model.LoginRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun LoginPage(navController: NavHostController,userViewModel: UserViewModel,isSignedIn: Boolean) {
    myapplicationTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Use Box to overlay the background image and the login screen content
            Box(modifier = Modifier.fillMaxSize()) {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds // Adjust the scaling as needed
                )

                // Login Screen Content
                LoginScreen(navController, userViewModel, isSignedIn)
            }

        }

    }
}

@Composable
fun LoginScreen(navController: NavHostController, userViewModel: UserViewModel,isSignedIn:Boolean) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF) // Define white color
    val context = LocalContext.current



    LaunchedEffect(isSignedIn) {
        if (isSignedIn) {
            userViewModel.loggedin()
            navController.navigate(Destination.MyReservations.route)
        }
    }
    // Inside LoginScreen composable function

    // Handle Google Sign-In button click
    val onGoogleSignInClick: () -> Unit = {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent
        (context as Activity).startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // ... (other composable content)




    // Collect login result as state
    val loginResultState = userViewModel.loginResult.collectAsState()

    // Access the value from the State object
    val loginResult = loginResultState.value

    // Handle login button click
//    val onLoginClick: () -> Unit = {
//        val loginRequest = LoginRequest(username, password)
//        userViewModel.checkUserExistence(loginRequest)
//    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Content
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign in to continue",
                style = TextStyle(color = Color.Black, fontSize = 24.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextButton(
                onClick = {
                    navController.navigate(Destination.SignUpPage.route)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Black)
            ) {
                Text("Sign Up", color = Black)
            }
            // Username TextField
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Password TextField
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Login Button
            CustomButton(
                onClick = {
                    val loginRequest = LoginRequest(username, password)
                    userViewModel.checkUserExistence(loginRequest)
                    // Display login result
                    if (loginResult != null) {
                        if (loginResult.success) {
                            userViewModel.loggedin()
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(Destination.MyReservations.route)
                        } else {
                             Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }
                    }}, // Call userExists when clicked

                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                backgroundColor = Color(0xFF27CEEB),
                contentColor = Color.White
            ) {
                Text("Sign in", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Don't have an account? ",
                color = Black
            )
            TextButton(
                onClick = {
                    navController.navigate(Destination.SignUpPage.route) // Replace "signup_route" with the actual route of your signup page
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Black)
            ) {
                Text("Sign Up", color = Black)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.weight(1f))
                Text("OR", color = Color.Gray)
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
            // New Column to wrap the Row containing the icons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Facebook Icon
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { /* Handle Facebook icon click */ }
                    )
                    Spacer(modifier = Modifier.width(42.dp)) // Added spacing between icons
                    // Google Icon
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(onClick = onGoogleSignInClick)
                    )
                    Spacer(modifier = Modifier.width(42.dp)) // Added spacing between icons
                    // Twitter Icon
                    Image(
                        painter = painterResource(id = R.drawable.twitter),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { /* Handle Twitter icon click */ }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    contentColor: Color,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier = modifier
            .background(
                color = if (isHovered) backgroundColor.copy(alpha = 0.8f) else backgroundColor,
                shape = RoundedCornerShape(16.dp) // Radius
            )
            .hoverable(interactionSource = interactionSource)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

//// Add this function to the same Kotlin file where you have your composable functions
//fun firebaseAuthWithGoogle(account: GoogleSignInAccount, context: Context,onSignInResult: (Boolean) -> Unit) {
//    val auth = FirebaseAuth.getInstance()
//    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id!!)
//    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//    auth.signInWithCredential(credential)
//        .addOnCompleteListener(context as Activity) { task ->
//            if (task.isSuccessful) {
//
//                // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "signInWithCredential:success")
//                val user = auth.currentUser
//
//                // Update UI or perform any necessary action upon successful sign-in
//                onSignInResult(true) // Assuming LoginPage holds the state
//            } else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                // Update UI or perform any necessary action upon sign-in failure
//                onSignInResult(false)
//            }
//        }
//}
