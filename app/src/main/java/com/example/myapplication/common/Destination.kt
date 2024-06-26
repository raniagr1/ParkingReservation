package com.example.myapplication.common

sealed class Destination(val route:String) {
    object  List: Destination("ParkingScreen")
    object  Home: Destination("HomePage")
    object  Details: Destination("details/{parkId}"){
        fun createRoute(parkId:Int) = "details/$parkId"
    }
    object  Reservation: Destination("Reservation/{parkId}"){
        fun createRoute(parkId:Int) = "Reservation/$parkId"
    }
    object  MyReservations: Destination("MyReservationsScreen")
    object  ReservationDetails: Destination("reservationDetails/{resId}"){
        fun createRoute(resId:Int) = "reservationDetails/$resId"
    }

    object  Login: Destination("LoginPage")
    object  SignUpPage: Destination("SignUpPage")

    object  MapPage: Destination("MapPage")
}