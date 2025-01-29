package sheridan.adolfo_david_romero.prototype_device.ui.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.util.UUID
/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-17
 **/

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BLEScreen(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bluetoothAdapter: BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    // Variables to track sensor data
    var temperature by remember { mutableStateOf("Temp: N/A") }
    var hr by remember { mutableStateOf("HR: N/A") }
    var spo2 by remember { mutableStateOf("SpO2: N/A") }
    var stepCount by remember { mutableStateOf("Steps: N/A") }
    var gpsData by remember { mutableStateOf("GPS: N/A") }
    var batteryLevel by remember { mutableStateOf("Battery: N/A") }

    // Store GATT instance for manual characteristic reads
    var gattInstance by remember { mutableStateOf<BluetoothGatt?>(null) }

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text("BLE Device") },
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
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            Button(onClick = {
                coroutineScope.launch {
                    connectToBLEDevice(
                        context = context,
                        bluetoothAdapter = bluetoothAdapter,
                        onTemperatureReceived = { temp -> temperature = temp },
                        onHRReceived = { heartRate -> hr = heartRate },
                        onSpO2Received = { oxygen -> spo2 = oxygen },
                        onStepCountReceived = { steps -> stepCount = steps },
                        onGPSReceived = { gps -> gpsData = gps },
                        onBatteryLevelReceived = { battery -> batteryLevel = battery },
                        onGattInstanceReady = { gatt -> gattInstance = gatt }
                    )
                }
            }, modifier = Modifier.padding(16.dp)) {
                Text("Connect to ESP32")
            }

            // Display each characteristic with a button for manual read
            CharacteristicRow("Temperature", temperature, gattInstance, TEMPERATURE_UUID)
            CharacteristicRow("Heart Rate", hr, gattInstance, HR_UUID)
            CharacteristicRow("SpO2", spo2, gattInstance, SPO2_UUID)
            CharacteristicRow("Steps", stepCount, gattInstance, STEP_COUNT_UUID)
            CharacteristicRow("GPS", gpsData, gattInstance, GPS_UUID)
            CharacteristicRow("Battery", batteryLevel, gattInstance, BATTERY_LEVEL_UUID)
        }
    }
}

@Composable
@SuppressLint("MissingPermission")
fun CharacteristicRow(label: String, value: String, gatt: BluetoothGatt?, uuid: UUID) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "$label: $value", modifier = Modifier.padding(bottom = 4.dp))
        Button(onClick = {
            gatt?.let {
                val characteristic = it.getService(SERVICE_UUID)?.getCharacteristic(uuid)
                if (characteristic != null) {
                    Log.d("BLE", "Manually reading characteristic: $label")
                    it.readCharacteristic(characteristic)
                } else {
                    Log.e("BLE", "Characteristic $label not found!")
                }
            }
        }) {
            Text("Read $label")
        }
    }
}

@SuppressLint("MissingPermission")
fun connectToBLEDevice(
    context: Context,
    bluetoothAdapter: BluetoothAdapter?,
    onTemperatureReceived: (String) -> Unit,
    onHRReceived: (String) -> Unit,
    onSpO2Received: (String) -> Unit,
    onStepCountReceived: (String) -> Unit,
    onGPSReceived: (String) -> Unit,
    onBatteryLevelReceived: (String) -> Unit,
    onGattInstanceReady: (BluetoothGatt?) -> Unit
) {
    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
        Log.e("BLE", "Bluetooth not supported or not enabled.")
        return
    }

    val bleScanner = bluetoothAdapter.bluetoothLeScanner

    val scanCallback = object : android.bluetooth.le.ScanCallback() {
        override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult?) {
            super.onScanResult(callbackType, result)
            val device = result?.device

            if (device != null && device.name == "Health Watch New_test") {
                Log.d("BLE", "ESP32 found, connecting...")
                bleScanner.stopScan(this)

                val gatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
                    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                        Log.d("BLE", "onConnectionStateChange: status=$status, newState=$newState")
                        if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                            Log.d("BLE", "Disconnected from GATT server.")
                            gatt.close()
                            onGattInstanceReady(null)
                        } else if (newState == BluetoothProfile.STATE_CONNECTED) {
                            Log.d("BLE", "Connected to GATT server.")
                            gatt.discoverServices()
                            onGattInstanceReady(gatt)
                        }
                        // Automatically send time after connection
                        Handler(Looper.getMainLooper()).postDelayed({
                            sendCurrentTime(gatt)
                        }, 2000) // Delay to ensure services are ready
                    }

                    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            Log.d("BLE", "Services discovered successfully")
                        } else {
                            Log.e("BLE", "Service discovery failed: status=$status")
                        }
                    }

                    override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            val value = characteristic.getStringValue(0) ?: "N/A"
                            when (characteristic.uuid) {
                                TEMPERATURE_UUID -> onTemperatureReceived(value)
                                HR_UUID -> onHRReceived(value)
                                SPO2_UUID -> onSpO2Received(value)
                                STEP_COUNT_UUID -> onStepCountReceived(value)
                                GPS_UUID -> onGPSReceived(value)
                                BATTERY_LEVEL_UUID -> onBatteryLevelReceived(value)
                            }
                        } else {
                            Log.e("BLE", "Failed to read characteristic: ${characteristic.uuid}")
                        }
                    }
                })
            } else {
                Log.e("BLE", "Device not found or invalid device name")
            }
        }
    }

    bleScanner.startScan(scanCallback)
    Log.d("BLE", "Scanning for devices...")
}

@SuppressLint("SimpleDateFormat", "MissingPermission")
fun sendCurrentTime(gatt: BluetoothGatt) {
    val timeCharacteristic = gatt.getService(SERVICE_UUID)?.getCharacteristic(TIME_UUID)
    if (timeCharacteristic != null) {
        // Get current time in HH:mm:ss format
        val currentTimeMillis = System.currentTimeMillis()
        val currentTime = java.text.SimpleDateFormat("HH:mm:ss").format(java.util.Date(currentTimeMillis))

        // Convert the time string to bytes
        val timeBytes = currentTime.toByteArray(Charsets.UTF_8)

        // Set the value of the time characteristic
        timeCharacteristic.value = timeBytes
        val success = gatt.writeCharacteristic(timeCharacteristic)

        if (success) {
            Log.d("BLE", "Time sent successfully: $currentTime")
        } else {
            Log.e("BLE", "Failed to send time.")
        }
    } else {
        Log.e("BLE", "Time characteristic not found!")
    }
}



// UUIDs for BLE characteristics
val SERVICE_UUID: UUID = UUID.fromString("6d8b471c-18d2-42e0-8054-2c028a8cc1fc")
val HR_UUID: UUID = UUID.fromString("b99b32d0-8927-4e42-a3c9-ff5a7cda1d4b")
val SPO2_UUID: UUID = UUID.fromString("d19c78e2-85b7-46d4-9c99-e6fe39450e63")
val STEP_COUNT_UUID: UUID = UUID.fromString("a631b16f-0bfa-4bda-99ea-b85fc7c5e1e9")
val TEMPERATURE_UUID: UUID = UUID.fromString("c87e2c28-09ed-4c3f-aab1-91181623e7d8")
val GPS_UUID: UUID = UUID.fromString("dcbfa6e8-5cbe-4d44-ae30-84f5b72a1234")
val BATTERY_LEVEL_UUID: UUID = UUID.fromString("dc2bc6e1-1ebe-496b-8cac-e987b6e82731") // Standard Battery Level UUID

//Android --> Watch
//FIXME: Convert time for John.
val TIME_UUID: UUID = UUID.fromString("06e3f1b6-e0ee-447c-a91b-9222e993a0a0") // Your ESP32 time characteristic UUID