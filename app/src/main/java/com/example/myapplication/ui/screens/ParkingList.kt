package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.viewmodels.ParkingViewModel

@Composable
fun Displayparkings(parkingModel: ParkingViewModel, navController: NavHostController) {

    val context = LocalContext.current
    Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
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
    }

}

@Composable
fun DisplayLoading(parkingModel: ParkingViewModel){
    if(parkingModel.loading.value){
        CircularProgressIndicator()
    }
}
/* Image(painter = painterResource(id = R.drawable.d1_lab),
            contentDescription = null,
            contentScale = ContentScale.Crop,
              modifier = Modifier
                  .weight(1f)
                  .padding(8.dp)
           )*/
@Composable
fun DisplayMessage(parkingModel: ParkingViewModel){
    val context = LocalContext.current
    if(parkingModel.displayMessage.value){
        Toast.makeText(context,"Une erreur s'est produit",Toast.LENGTH_SHORT).show()
    }
}
