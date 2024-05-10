package com.example.myapplication

import ParkingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.DisplayDetail
import com.example.myapplication.ui.screens.Displayparkings
import com.example.myapplication.ui.theme.myapplicationTheme
import com.example.myapplication.ui.viewmodels.ParkingViewModel

class MainActivity : ComponentActivity() {
    private val pModel : ParkingViewModel by viewModels {
        ParkingViewModel.Factory((application as MyApplication).parkingRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            myapplicationTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize() ,
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                   /* var i = remember { mutableStateOf(0) }
                    val onClick = { i.value+=1 }
                    Count(i.value,onClick)*/
                    NavigationExample(navController = rememberNavController(),pModel)

                   // Displayparkings(pModel, navController)
                            pModel.getAllparkings()

                }
            }
        }
    }
}
@Composable
fun NavigationExample(navController: NavHostController, parkingViewModel: ParkingViewModel){

        androidx.navigation.compose.NavHost(navController = navController, startDestination = "ParkingScreen") {
          composable(Destination.List.route) { ParkingScreen(parkingViewModel, navController) }
            composable(Destination.Details.route) {
                val parkId = it.arguments?.getString("parkId")?.toInt()
                DisplayDetail(parkId ?: 1, parkingViewModel,navController)
            }

         /*   composable(Destination.Reservation.route) { ReservationBookingScreen( ) }
            composable(Destination.MyReservations.route) { MyReservationsScreen(reservationViewModel )
             }*/

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

/*
*
*
*
/*class MainActivity : ComponentActivity() {
    private val db by lazy {
        AppDatabase.getDatabase(applicationContext) // Use the getDatabase() method to retrieve the Room database instance
    }

    private val pModel :ParkingViewModel by viewModels {
        ParkingViewModel.Factory((application as MyApplication).parkingRepository)
    }
    private val reservationViewModel by viewModels<ReservationsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                return ReservationsViewModel(db.reservationDao()) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeTab = TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home, destination =Destination.Home.route )
            val parkingListTab = TabBarItem(title = "Parking List", selectedIcon = Icons.Filled.List, unselectedIcon = Icons.Outlined.List, badgeAmount = 7, destination =Destination.List.route )
            val mapTab = TabBarItem(title = "Map", selectedIcon = Icons.Filled.LocationOn, unselectedIcon = Icons.Outlined.LocationOn, destination =Destination.Home.route )
            val myReservationsTab = TabBarItem(title = "my Reservations", selectedIcon = Icons.Filled.DateRange, unselectedIcon = Icons.Outlined.DateRange, destination =Destination.MyReservations.route )

            // creating a list of all the tabs
            val tabBarItems = listOf(homeTab, parkingListTab, mapTab, myReservationsTab)


            TryingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    TopPart()
                    NavigationExample(navController = rememberNavController(),pModel,tabBarItems, reservationViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationExample(navController: NavHostController, parkingViewModel: ParkingViewModel,tabBarItems :List<TabBarItem>,reservationViewModel: ReservationsViewModel){
    Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
    androidx.navigation.compose.NavHost(navController = navController, startDestination = "ParkingScreen") {
        composable(Destination.Home.route) { HomePage( navController) }
        composable(Destination.List.route) { ParkingScreen(parkingViewModel, navController) }
        composable(Destination.Details.route) {
            val parkId = it.arguments?.getString("parkId")?.toInt()
            DisplayDetail(parkId ?: 0, parkingViewModel,navController)
        }

        composable(Destination.Reservation.route) { ReservationBookingScreen( ) }
        composable(Destination.MyReservations.route) { MyReservationsScreen(reservationViewModel ) }
    }}
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
*/*/

