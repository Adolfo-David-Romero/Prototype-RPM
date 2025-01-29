package sheridan.adolfo_david_romero.prototype_device.ui.connectivity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException
import okhttp3.Credentials
import sheridan.adolfo_david_romero.prototype_device.domain.api.DigestAuthenticator
import java.util.concurrent.TimeUnit

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-30
 **/
@Composable
fun ConnectionTestScreen(cameraIp: String, username: String, password: String) {
    var connectionResult by remember { mutableStateOf("Press the button to test connection") }
    val coroutineScope = rememberCoroutineScope()
    val client = OkHttpClient()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(connectionResult)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                val authenticator = DigestAuthenticator(client, username, password)
                connectionResult = authenticator.authenticate("http://$cameraIp/cgi-bin/magicBox.cgi?action=getSystemInfo")
                Log.d("ConnectionTestScreen", "Connection result: $connectionResult")
            }
        }) {
            Text("Test Connection")
        }
    }
}