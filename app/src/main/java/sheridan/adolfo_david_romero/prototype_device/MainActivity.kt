package sheridan.adolfo_david_romero.prototype_device

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import sheridan.adolfo_david_romero.prototype_device.ui.common.AppNavigation
import sheridan.adolfo_david_romero.prototype_device.ui.home.HomeScreen
import sheridan.adolfo_david_romero.prototype_device.ui.theme.PrototypeDeviceTheme

class MainActivity : ComponentActivity() {
    // Define a launcher for the permission request
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>
    private val PERMISSION_REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        // Initialize the permission launcher
        setupPermissionsLauncher()

        // Request permissions on app start
        requestCorePermissions()
        setContent {
            PrototypeDeviceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavigation(navController)
                }
            }
        }
    }
    private fun setupPermissionsLauncher() {
        requestPermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                permissions.entries.forEach { entry ->
                    val permission = entry.key
                    val isGranted = entry.value

                    if (isGranted) {
                        Log.d("Permissions", "$permission granted")
                    } else {
                        Log.e("Permissions", "$permission denied")
                    }
                }
            }
    }
    private fun requestCorePermissions() {
        val permissionsToRequest = REQUIRED_PERMISSIONS.filter {
            checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }
        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    }

}






// Helper function to check permissions
fun hasPermission(context: android.content.Context, permission: String): Boolean {
    return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PrototypeDeviceTheme {
        HomeScreen(
            onNavigateToStream = {},
            onNavigateToRTSPStream = {},
            onNavigateToTracking = {},
            onNavigateToMedication = {},
            onNavigateToPatientProfile = {},
            onNavigateToBLE = {},
            onNavigateToConnectivity = {},
            onNavigateToTestAudio = {},
        )
    }
}