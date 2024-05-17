package com.example.myapplication.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.myapplicationTheme
import androidx.compose.runtime.remember
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.Destination
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.data.model.User
import com.example.myapplication.ui.viewmodels.UserViewModel
import com.example.trying.R
import java.util.UUID
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun SignUp(navController: NavHostController,userViewModel: UserViewModel,coroutineScope: CoroutineScope) {
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
                SignUpScreen(navController,userViewModel,coroutineScope)
            }

        }

    }
}
@Composable
fun SignUpScreen(navController: NavHostController,userViewModel: UserViewModel,coroutineScope: CoroutineScope) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }

    val context = LocalContext.current // Get the context for SharedPreferences

    Box(
        modifier = Modifier.fillMaxSize() // Only fillMaxSize here
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
                text = "Sign Up to continue",
                style = TextStyle(color = Color.Black, fontSize = 24.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = firstname,
                onValueChange = { firstname = it },
                label = { Text("Firstname", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            OutlinedTextField(
                value = lastname ,
                onValueChange = { lastname  = it },
                label = { Text("Lastname ", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)

            )
            OutlinedTextField(
                value = username ,
                onValueChange = { username  = it },
                label = { Text("username ", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            CustomButton(
                onClick = {
                    val newUser = User(
                        firstName = firstname,
                        lastName = lastname,
                        userName = username,
                        passwrd = password
                    )
                    coroutineScope.launch {
                        val isSuccess = userViewModel.registerUser(newUser)
                        if (isSuccess) {
                            // Registration successful, navigate to reservations page
                            navController.navigate(Destination.MyReservations.route)
                        } else {
                            // Registration failed, handle error
                            // For example, show a snackbar or toast
                            navController.navigate(Destination.List.route)
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp), // Adjust size
                backgroundColor = Color(0xFF27CEEB), // Sky blue color
                contentColor = Color.White
            ) {
                Text("Sign Up", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Already have an account?",
                color = Black
            )
            TextButton(
                onClick = {  navController.navigate(Destination.Login.route) },
                colors = ButtonDefaults.textButtonColors(contentColor = Black)
            ) {
                Text("Log in", color = Black)
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
                    // Google Icon

                    // Facebook Icon
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { /* Handle Twitter icon click */ }
                    )
                    Spacer(modifier = Modifier.width(42.dp)) // Added spacing between icons
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { /* Handle Twitter icon click */ }
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

fun generateId(): Int {
    // Generate a random UUID
    val uuid = UUID.randomUUID()
    // Get the least significant bits of the UUID
    val leastSignificantBits = uuid.leastSignificantBits
    // Convert the least significant bits to an integer
    // Note: This might result in negative integers, so you may want to take the absolute value or use unsigned types
    return leastSignificantBits.toInt()
}


/*@Composable
fun SignUpCustomButton(
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
}*/
