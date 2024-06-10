/*package com.example.myapplication.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.data.model.Parking
import com.example.myapplication.ui.viewmodels.ParkingViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


@Composable
fun MapPage(parkingViewModel: ParkingViewModel) {
    val applicationContext = LocalContext.current.applicationContext

    val mapView = remember {
        MapView(applicationContext)
    }
    val parkingLocations = parkingViewModel.data.value

    LaunchedEffect(Unit) {

        parkingViewModel.getAllparkings()
    }

    AndroidView({ mapView }) { mapView ->
        mapView.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 10f))

            displayParkingLocations(googleMap, parkingLocations)
        }
    }
}

private fun displayParkingLocations(googleMap: GoogleMap, parkingLocations: List<Parking>) {
    googleMap.clear()
    for (parking in parkingLocations) {
        val location = LatLng(parking.latitude, parking.longitude)
        googleMap.addMarker(
            MarkerOptions().position(location).title(parking.name)
        )
    }
}*/

package com.example.myapplication.ui.screens

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.myapplication.data.model.Parking
import com.example.myapplication.ui.viewmodels.ParkingViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


// Modify the displayParkingLocations function to accept a list of parking locations
//private fun displayParkingLocations(googleMap: GoogleMap, parkingLocations: List<LatLng>,parkingLocations: List<Parking>) {
//    for (location in parkingLocations) {
//        googleMap.addMarker(
//            MarkerOptions().position(location).title("Parking Spot")
//        )
//    }
//}
private fun displayParkingLocations(googleMap: GoogleMap, parkingLocations: List<Parking>) {
    googleMap.clear()
    for (parking in parkingLocations) {
        val location = LatLng(parking.latitude, parking.longitude)

        // Create a Marker with a custom info window
        val marker = googleMap.addMarker(MarkerOptions()
            .position(location)
            .title(parking.name))

        // Set a custom info window for the marker
        if (marker != null) {
            marker.snippet = createParkingInfoWindow(parking)
        }
        if (marker != null) {
            marker.showInfoWindow()
        } // Optionally show info window immediately
    }
}

// This function creates the content for the info window
private fun createParkingInfoWindow(parking: Parking): String {
    val infoWindowText = StringBuilder()


    // Add additional details from Parking object

    infoWindowText.append("Capacity: ${parking.capacity} cars | ")
    infoWindowText.append("Price per hour: €${String.format("%.2f", parking.pricePerHour)}")


    // You can add more details like address (commune), rating, etc.

    return infoWindowText.toString()
}
@Composable
fun MapPage(viewModel: ParkingViewModel) {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()
    val parkings = viewModel.data.value
    val parkingLocations = viewModel.data.value.map { parking ->
        LatLng(parking.latitude, parking.longitude)
    }
    AndroidView({ mapView }) { mapView ->
        mapView.getMapAsync { googleMap ->
            // Customize your map
            val newYork = LatLng(40.7128, -74.0060)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newYork, 10f))

            // Display parking locations on the map
            displayParkingLocations(googleMap,parkings)
        }
    }
}

// RememberMapViewWithLifecycle helper function
@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    return mapView
}

