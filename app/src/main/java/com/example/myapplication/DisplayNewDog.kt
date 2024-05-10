package com.example.myapplication
/*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import javax.sql.RowSetWriter

@Composable
fun DisplayNewparking() {


    val s ="Labs are currently the most popular parking in the U.S., and with good reason — this family-friendly companion is good-natured, hard-working and covers all the bases as man’s best friend. \nLabs still work with hunters in retrieving game as they did centuries ago, but they also work as search and rescue, service and show parkings, and make a welcome addition to any home"

    Row {
        /* Image(painter = painterResource(id = R.drawable.d1_lab),
              contentDescription = null,
              contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
             )*/

        Column(
            modifier = Modifier.weight(2f)
        ) {
        Text(text = "Labrador retriever" , fontWeight = FontWeight.Bold,
            fontSize = 12.sp
            )
Spacer(modifier = Modifier.height(8.dp))
            Text(text = s,
                fontSize = 11.sp
            )
        }
    }

}*/