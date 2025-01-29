package sheridan.adolfo_david_romero.prototype_device.ui.audiostreaming

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import sheridan.adolfo_david_romero.prototype_device.domain.audiostreaming.AudioStreaming

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-27
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreamingScreen(username: String, password: String, cameraIp: String) {
    var connectionStatus by remember { mutableStateOf("Not Connected") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Audio Streaming") }) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = connectionStatus, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            connectionStatus = AudioStreaming.startStreaming(username, password, cameraIp)
                        }
                    }
                ) {
                    Text("Start Audio Streaming")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        AudioStreaming.stopStreaming()
                        connectionStatus = "Disconnected"
                    }
                ) {
                    Text("Stop Audio Streaming")
                }
            }
        }
    )
}