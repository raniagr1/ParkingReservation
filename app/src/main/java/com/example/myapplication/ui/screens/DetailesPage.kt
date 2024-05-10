package com.example.myapplication.ui.screens

/*
@Composable
fun DisplayDetail(parkingId: Int?, parkingViewModel: ParkingViewModel?, navController: NavHostController) {
   /* var parking by remember {
        mutableStateOf<Parking?>(null)
    }

    LaunchedEffect(Unit) {
        parkingViewModel?.let { viewModel ->
            var result = viewModel.getParkingById(parkingId)
            parking = result
        }
    }

    parking?.let { park ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = com.example.myapplication.R.drawable.car2),
                    contentDescription = "Tomato Image",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(44.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .background(Color.Blue)
                    .padding(24.dp)
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Sawi Hijau",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Rs. 14.00/Kg",
                        color = Color.DarkGray,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Sawi hijau mengandung folat, kalium, vitamin C, dan vitamin B-6 dan rendah kolesterol. Perpaduan ini embantu menjaga kesehatan jantung. Vitamin B-6 dan folat mencegah penumpukan senyawa yang dikenal sebagal homocysteine.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Total",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Text(
                            text = "Rs. 14.00/Kg",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    ),
                    onClick = {navController.navigate(Destination.Reservation.route)  }) {
                    Text(
                        text = "Book a place",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    } ?: run {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Liste des parkings $parkingId",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }*/
}
*/