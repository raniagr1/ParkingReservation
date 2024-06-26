import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.presentation.ui.screens.ParkingListItem
import com.example.myapplication.presentation.viewmodels.ParkingViewModel


//import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingScreen(parkingModel: ParkingViewModel, navController: NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxHeight(0.91F),
        verticalArrangement = Arrangement.Top
    ) {
        // Top Row with Welcome message, Back button, and Notifications button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            IconButton(
                onClick = {
                    // Handle back button action
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary// Sky blue color
                )
            }
            Text(
                text = "Welcome!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF0087de), // White color
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            // Notification button
            IconButton(
                onClick = {
                    // Handle notification button action
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.primary // Sky blue color
                )
            }
        }

        // Title
        Text(
            text = "Parkings List",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF0087de),
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        DisplayLoading(parkingModel)

        LazyColumn {
            items(parkingModel.data.value) { parking ->
                ParkingListItem(parking = parking,navController)
            }
        }
        DisplayMessage(parkingModel)
    }
  /* Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        DisplayLoading(parkingModel)
        LazyColumn {
            items(parkingModel.data.value) {
                Row (

                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .padding(4.dp)
                        .background(Color(0xFFE0E0E0))
                        .clickable {
                            Toast
                                .makeText(context, it.name, Toast.LENGTH_SHORT)
                                .show()
                        }


                ) {


                    Column(
                        modifier = Modifier.weight(2f)
                    ) {
                        Text(text = it.name , fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it.description,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        DisplayMessage(parkingModel)
    }*/

}

@Composable
fun DisplayLoading(parkingModel: ParkingViewModel){
    if(parkingModel.loading.value){
        CircularProgressIndicator()
    }
}

@Composable
fun DisplayMessage(parkingModel: ParkingViewModel){
    val context = LocalContext.current
    if(parkingModel.displayMessage.value){
        Toast.makeText(context,"Une erreur s'est produit",Toast.LENGTH_SHORT).show()
    }
}
  //  val parkingList = parkingViewModel.allParkings.collectAsState()

