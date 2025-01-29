package sheridan.adolfo_david_romero.prototype_device.ui.location

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 **/
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.MarkerState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveTrackingScreen(navController: NavHostController) {
    val userLocation = LatLng(43.6532, -79.3832) // Replace with desired coordinates

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text("Live Tracking") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = { navController.popBackStack() }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        GoogleMapView(
            userLocation = userLocation,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        )
    }
}

@Composable
fun GoogleMapView(userLocation: LatLng, modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 14f)
    }

    val markerState = remember { MarkerState(position = userLocation) }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = markerState,
            title = "User Location",
            snippet = "This is where the user is currently located."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LiveTrackingScreenPreview() {
    LiveTrackingScreen(navController = rememberNavController())
}