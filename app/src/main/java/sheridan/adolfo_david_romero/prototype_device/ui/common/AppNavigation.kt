package sheridan.adolfo_david_romero.prototype_device.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sheridan.adolfo_david_romero.prototype_device.ui.ble.BLEScreen
import sheridan.adolfo_david_romero.prototype_device.ui.connectivity.ConnectionTestScreen
import sheridan.adolfo_david_romero.prototype_device.ui.connectivity.TestAudioScreen
import sheridan.adolfo_david_romero.prototype_device.ui.home.HomeScreen
import sheridan.adolfo_david_romero.prototype_device.ui.location.LiveTrackingScreen
import sheridan.adolfo_david_romero.prototype_device.ui.medication.MedicationReminderScreen
import sheridan.adolfo_david_romero.prototype_device.ui.patient.PatientProfileScreen
import sheridan.adolfo_david_romero.prototype_device.ui.audiostreaming.StreamingScreen
import sheridan.adolfo_david_romero.prototype_device.ui.videostreaming.RTSPStreamingScreen

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-27
 **/
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToStream = { navController.navigate("streaming") },
                onNavigateToRTSPStream = {navController.navigate("rtspStream") },
                onNavigateToTracking = { navController.navigate("tracking") },
                onNavigateToMedication = { navController.navigate("medication") },
                onNavigateToPatientProfile = { navController.navigate("patientProfile") },
                onNavigateToBLE = { navController.navigate("ble") },
                onNavigateToConnectivity = { navController.navigate("connectivity") },
                onNavigateToTestAudio = {navController.navigate("testAudio")},
            )
        }
        composable("streaming") {
            StreamingScreen("admin","Trafalgar1430","192.168.2.94")
        }
        composable("rtspStream") {
            RTSPStreamingScreen("192.168.2.94","")
        }
        composable("tracking") {
            LiveTrackingScreen(navController)
        }
        composable("medication") {
            MedicationReminderScreen(navController)
        }
        composable("patientProfile") {
            PatientProfileScreen(navController)
        }
        composable("ble") { // New composable for BLE screen
            BLEScreen(navController)
        }
        composable("connectivity") { // New composable for BLE screen
            ConnectionTestScreen("192.168.2.94:80","admin","Trafalgar1430")
        }
        composable("testAudio") { // New composable for BLE screen
            TestAudioScreen("192.168.2.94:80","admin","Trafalgar1430")
        }
    }
}