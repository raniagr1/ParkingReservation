package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.Destination
import com.example.myapplication.URL
import com.example.myapplication.data.model.Parking
import com.example.myapplication.ui.viewmodels.ParkingViewModel
import com.example.trying.R

@Composable
fun DisplayDetail(parkingId: Int, parkingViewModel: ParkingViewModel, navController: NavHostController) {
    var parking by remember { mutableStateOf<Parking?>(null) }

    LaunchedEffect(key1 = parkingId) {
        parkingViewModel.getParkingById(parkingId)
    }

    parking = parkingViewModel._parking.value

    parking?.let { park ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 120.dp) // Added padding to avoid overlap with TabView
                    .verticalScroll(rememberScrollState())
            ) {
                // Loading indication
                DisplayLoading(parkingViewModel)

                // Parking Image and Rating Badge
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    AsyncImage(
                        model = URL + park.image,
                        placeholder = painterResource(id = R.drawable.parking3),
                        error = painterResource(id = R.drawable.parking3),
                        contentDescription = "Parking Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                    )
                    RatingBadge(rating = park.rating.toDouble(), modifier = Modifier.align(Alignment.TopEnd))
                }

                // Parking Details
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = park.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color(0xFF0087de),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = "Located in ${park.commune}",
                            color = Color(0xFF578dfd),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )


                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                            ) {

                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = "Money Icon",
                                tint = Color(0xFF0087de),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Price: ${park.pricePerHour} DA",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                ///modifier = Modifier.padding(top = 4.dp)
                            )}
                    }

                    Text(

                        text = "Description ",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(

                        text = park.description,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                }
            }

            // Booking Button at the Bottom Center
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.91F)
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0087de)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        navController.navigate(Destination.Reservation.createRoute(park.id))
                    }
                ) {
                    Text(
                        text = "Book a place",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    } ?: run {
        // Handle case when parking details are not available
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Parking details not available.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun RatingBadge(rating: Double, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(Color.White.copy(alpha = 0.7f), shape = RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = rating.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF0a58fb)
            )
        }
    }
}
