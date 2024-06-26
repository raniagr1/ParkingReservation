package com.example.myapplication.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.common.Destination
import com.example.myapplication.common.URL
import com.example.myapplication.data.model.Parking
import com.example.trying.R

@Composable
fun ParkingListItem(parking: Parking, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                navController.navigate(Destination.Details.createRoute(parking.id))
            },

        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)

        ) {
            AsyncImage(
                model = URL +parking.image
                ,

               // placeholder = painterResource(id = R.drawable.parking3),
                error = painterResource(id = R.drawable.parking3),
                contentDescription = null,
                        modifier = Modifier
                        .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            // Parking Image
        /*    Image(
                painter = painterResource(id = R.drawable.car2), // Replace with actual image resource
                contentDescription = "Parking Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
*/
            Spacer(modifier = Modifier.width(16.dp))

            // Parking Information
            Column {
                Text(
                    text = parking.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF578dfd)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star Icon",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = parking.rating.toString(),
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon",
                        tint = Color(0xFF0087de),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = parking.commune,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Money Icon",
                    tint = Color(0xFF0087de),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Price: ${parking.pricePerHour} DA",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )}
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Car Icon",
                        tint = Color(0xFF0087de),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Capacity: ${parking.capacity}",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}