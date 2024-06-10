package com.example.myapplication

import ParkingScreen
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.FirebaseMessagingScreen
import com.example.myapplication.ui.screens.DisplayDetail
import com.example.myapplication.ui.screens.Displayparkings
import com.example.myapplication.ui.screens.MyReservationsScreen
import com.example.myapplication.ui.screens.ReservationBookingScreen
import com.example.myapplication.ui.screens.ReservationDetails
import com.example.myapplication.ui.theme.myapplicationTheme
import com.example.myapplication.ui.viewmodels.ParkingViewModel
import com.example.myapplication.ui.viewmodels.ReservationsViewModel
import com.example.myapplication.ui.viewmodels.TokenViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : ComponentActivity() {

    private val pModel: ParkingViewModel by viewModels {
        ParkingViewModel.Factory((application as MyApplication).parkingRepository)
    }
    private val rModel: ReservationsViewModel by viewModels {
        ReservationsViewModel.Factory((application as MyApplication).reservationRepository)
    }
    private val tokenViewModel: TokenViewModel by viewModels {
        TokenViewModel.Factory((application as MyApplication).tokenRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch the FCM token and send it to the server
        fetchAndSendFCMToken()

        setContent {
            val parkingListTab = TabBarItem(
                title = "Parking List",
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List,
                badgeAmount = 7,
                destination = Destination.List.route
            )
            val mapTab = TabBarItem(
                title = "Map",
                selectedIcon = Icons.Filled.LocationOn,
                unselectedIcon = Icons.Outlined.LocationOn,
                destination = Destination.MyReservations.route
            )
            val myReservationsTab = TabBarItem(
                title = "my Reservations",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange,
                destination = Destination.MyReservations.route
            )

            val tabBarItems = listOf(parkingListTab, mapTab, myReservationsTab)

            myapplicationTheme {
                FirebaseMessagingScreen()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    handleDeepLinks(navController) // Handle the intent when the app is opened via the notification

                    TopPart()
                    NavigationExample(navController, pModel, rModel, tabBarItems)
                    pModel.getAllparkings()
                }
            }
        }

    }
    private fun handleDeepLinks(navController: NavHostController) {
        // Postpone handling to ensure the navigation components are ready
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            intent?.data?.let { data ->
                if (data.scheme == "myapp") {
                    val reservationId = data.lastPathSegment
                    Log.d("DeepLink", "Parsed reservationId: $reservationId")

                    if (reservationId != null) {
                        try {
                            val reservId = reservationId.toInt()
                            Log.d("DeepLink", "Navigating to ReservationDetails with ID: $reservId")

                            navController.navigate(Destination.ReservationDetails.createRoute(reservId))
                        } catch (e: NumberFormatException) {
                            Log.e("DeepLink", "Invalid reservation ID format: $reservationId")
                        }
                    }
                }
            }
        }
    }

    private fun fetchAndSendFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("FCM", "FCM Token: $token")
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()

            // Assume user ID is available; replace with actual user ID as needed
            val userId = 1

            // Send the token to the server using TokenViewModel
            tokenViewModel.sendToken(token, userId)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationExample(
    navController: NavHostController,
    parkingViewModel: ParkingViewModel,
    reservationsViewModel: ReservationsViewModel,
    tabBarItems: List<TabBarItem>
) {
    Scaffold(
        bottomBar = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TabView(tabBarItems, navController)
            }
        }
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        NavHost(navController = navController, startDestination = "ParkingScreen") {
            composable(Destination.List.route) { ParkingScreen(parkingViewModel, navController) }
            composable(Destination.Details.route) {
                val parkId = it.arguments?.getString("parkId")?.toInt()
                DisplayDetail(parkId ?: 1, parkingViewModel, navController)
            }
            composable(Destination.Reservation.route) {
                val parkId = it.arguments?.getString("parkId")?.toInt()
                ReservationBookingScreen(parkId ?: 1, reservationsViewModel, parkingViewModel, navController)
            }
            composable(Destination.ReservationDetails.route) {
                val resId = it.arguments?.getString("resId")?.toInt()
                ReservationDetails(resId ?: 0, reservationsViewModel)
            }
            composable(Destination.MyReservations.route) { MyReservationsScreen(reservationsViewModel, navController) }
        }
    }
}

@Composable
fun Count(i:Int,onClick:()->Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(8.dp)
            .background(Color(0xFFEF5350))

    ) {
        Button(onClick = onClick) {
            Text(text = "The number is ${i}")
        }

        }
    }
data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null,
    val destination:String
)

@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Spacer(modifier = Modifier.height(50.dp))
    NavigationBar {
        // looping over each tab to generate the views and navigation for each item
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.destination)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
                label = {Text(tabBarItem.title)})
        }
    }
}

// This component helps to clean up the API call from our TabView above,
// but could just as easily be added inside the TabView without creating this custom component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {

    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
        )
    }
}

// This component helps to clean up the API call from our TabBarIconView above,
// but could just as easily be added inside the TabBarIconView without creating this custom component
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}
// end of the reusable components that can be copied over to any new projects
// ----------------------------------------

// This was added to demonstrate that we are infact changing views when we click a new tab
@Composable
fun MoreView() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Thing 1")
        Text("Thing 2")
        Text("Thing 3")
        Text("Thing 4")
        Text("Thing 5")
    }
}

@Composable
fun TopPart() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffeceef0)), Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "Menu Icon",
            Modifier

                .clip(CircleShape)
                .size(40.dp),
            tint = Color.Black,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Location", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Red,
                )
                Text(text = "Accra" , color = Color.Black)
            }

        }
        Icon(
            imageVector = Icons.Default.Notifications, contentDescription = "Notification Icon",

            Modifier
                .size(45.dp),
            tint = Color.Black,
        )
    }
}



