package sheridan.adolfo_david_romero.prototype_device.ui.connectivity

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-31
 **/

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.security.MessageDigest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestAudioScreen(cameraIp: String, username: String, password: String) {
    val client = remember { OkHttpClient() }
    val scope = rememberCoroutineScope()

    
    var inputChannelResult by remember { mutableStateOf("Press button to test") }
    var outputChannelResult by remember { mutableStateOf("Press button to test") }
    var volumeResult by remember { mutableStateOf("Press button to test") }
    var audioStreamResult by remember { mutableStateOf("Press button to test") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Test Audio Endpoints") }) },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item{ Text("Input Channel Result: $inputChannelResult") }
                item{
                    Button(onClick = {
                        scope.launch {
                            inputChannelResult =
                                testGetAudioInputChannel(cameraIp, username, password, client)
                        }
                    }) {
                        Text("Test Audio Input Channel")
                    }
                }

                item{ Text("Output Channel Result: $outputChannelResult") }
                item{
                    Button(onClick = {
                        scope.launch {
                            outputChannelResult =
                                testGetAudioOutputChannel(cameraIp, username, password, client)
                        }
                    }) {
                        Text("Test Audio Output Channel")
                    }
                }

                item{ Text("Volume Result: $volumeResult") }
                item{
                    Button(onClick = {
                        scope.launch {
                            volumeResult = testGetVolume(cameraIp, username, password, client)
                        }
                    }) {
                        Text("Get Volume")
                    }
                }

                item{ Text("Audio Stream Result: $audioStreamResult") }
                item{
                    Button(onClick = {
                        scope.launch {
                            audioStreamResult =
                                testGetAudioStream(cameraIp, username, password, client)
                        }
                    }) {
                        Text("Test Audio Stream")
                    }
                }
            }
        }
    )
}

suspend fun testGetAudioInputChannel(cameraIp: String, username: String, password: String, client: OkHttpClient): String {
    val url = "http://$cameraIp/cgi-bin/devAudioInput.cgi?action=getCollect"
    return makeAuthenticatedRequest(url, username, password, client)
}

suspend fun testGetAudioOutputChannel(cameraIp: String, username: String, password: String, client: OkHttpClient): String {
    val url = "http://$cameraIp/cgi-bin/devAudioOutput.cgi?action=getCollect"
    return makeAuthenticatedRequest(url, username, password, client)
}

suspend fun testGetVolume(cameraIp: String, username: String, password: String, client: OkHttpClient): String {
    val url = "http://$cameraIp/cgi-bin/configManager.cgi?action=getConfig&name=AudioOutputVolume"
    return makeAuthenticatedRequest(url, username, password, client)
}

suspend fun testGetAudioStream(cameraIp: String, username: String, password: String, client: OkHttpClient): String {
    val url = "http://$cameraIp/cgi-bin/audio.cgi?action=getAudio&httptype=singlepart&channel=1"
    return makeAuthenticatedRequest(url, username, password, client)
}

suspend fun makeAuthenticatedRequest(url: String, username: String, password: String, client: OkHttpClient): String {
    return withContext(Dispatchers.IO) {
        try {
            Log.d("TestAudioScreen", "Making unauthenticated request to $url")
            val initialRequest = Request.Builder().url(url).build()
            val initialResponse = client.newCall(initialRequest).execute()

            if (initialResponse.code == 401) {
                val authHeader = initialResponse.header("WWW-Authenticate")
                if (authHeader != null) {
                    val authDetails = parseAuthDetails(authHeader)
                    val authorizationHeader = buildDigestHeader(url, authDetails, username, password)

                    Log.d("TestAudioScreen", "Built Digest Authorization header: $authorizationHeader")

                    val authenticatedRequest = Request.Builder()
                        .url(url)
                        .header("Authorization", authorizationHeader.toString())
                        .build()

                    val authenticatedResponse = client.newCall(authenticatedRequest).execute()
                    Log.d("TestAudioScreen", "Authenticated Response Code: ${authenticatedResponse.code}")
                    Log.d("TestAudioScreen", "Authenticated Response Headers: ${authenticatedResponse.headers}")

                    // Specifically handle streaming audio data
                    if (url.contains("audio.cgi")) {
                        authenticatedResponse.body?.use { body ->
                            val buffer = ByteArray(4096) // Buffer for streaming data
                            val inputStream = body.byteStream()
                            var bytesRead: Int

                            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                                Log.d("TestAudioScreen", "Received audio data chunk of size: $bytesRead")
                                // Here, you can write buffer data to an audio player or file
                            }
                        }
                        return@withContext "Audio streaming data received"
                    }

                    return@withContext handleResponse(authenticatedResponse)
                } else {
                    return@withContext "Authentication header missing in 401 response"
                }
            } else {
                return@withContext handleResponse(initialResponse)
            }
        } catch (e: IOException) {
            Log.e("TestAudioScreen", "Request Error: ${e.message}")
            "Request Error: ${e.message}"
        }
    }
}

fun parseAuthDetails(header: String): Map<String, String> {
    return header.removePrefix("Digest ").split(", ").associate {
        val (key, value) = it.split("=")
        key to value.trim('"')
    }
}

fun buildDigestHeader(url: String, authDetails: Map<String, String>, username: String, password: String): String? {
    val realm = authDetails["realm"] ?: return null
    val nonce = authDetails["nonce"] ?: return null
    val qop = authDetails["qop"] ?: "auth"
    val nc = String.format("%08x", 1)
    val cnonce = System.currentTimeMillis().toString(16)

    val ha1 = md5("$username:$realm:$password")
    val ha2 = md5("GET:$url")
    val response = md5("$ha1:$nonce:$nc:$cnonce:$qop:$ha2")

    return "Digest username=\"$username\", realm=\"$realm\", nonce=\"$nonce\", uri=\"$url\", " +
            "qop=$qop, nc=$nc, cnonce=\"$cnonce\", response=\"$response\""
}

fun md5(data: String): String {
    return MessageDigest.getInstance("MD5").digest(data.toByteArray()).joinToString("") {
        "%02x".format(it)
    }
}

fun handleResponse(response: Response): String {
    return if (response.isSuccessful) {
        "Success: ${response.body?.string()}"
    } else {
        "Request Failed: ${response.code} ${response.message}"
    }
}