package sheridan.adolfo_david_romero.prototype_device.domain.api

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-31
 **/
import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

object TestAudioSender {

    suspend fun sendTestAudioFile(
        context: Context,
        cameraIp: String,
        username: String,
        password: String,
        filePath: String
    ): String {
        val client = OkHttpClient()
        val url = "http://$cameraIp/cgi-bin/audio.cgi?action=postAudio&httptype=singlepart&channel=1"

        return withContext(Dispatchers.IO) {
            try {
                Log.d("TestAudioSender", "Making initial unauthenticated request to $url")
                val initialRequest = Request.Builder().url(url).build()
                val initialResponse = client.newCall(initialRequest).execute()

                if (initialResponse.code == 401) {
                    val authHeader = initialResponse.header("WWW-Authenticate")
                    if (authHeader != null) {
                        val authDetails = parseAuthDetails(authHeader)
                        val authorizationHeader = buildDigestHeader(url, authDetails, username, password)

                        val connection = URL(url).openConnection() as HttpURLConnection
                        connection.apply {
                            requestMethod = "POST"
                            doOutput = true
                            setRequestProperty("Authorization", authorizationHeader)
                            setRequestProperty("Content-Type", "audio/g711a")
                        }

                        val audioFile = File(filePath)
                        if (!audioFile.exists()) return@withContext "Test audio file not found."

                        connection.outputStream.use { outputStream ->
                            sendAudioFile(outputStream, audioFile)
                        }
                        connection.disconnect()
                        return@withContext "Test audio sent to camera successfully"
                    } else {
                        return@withContext "Authentication header missing in 401 response"
                    }
                }
                "Failed to authenticate"
            } catch (e: Exception) {
                Log.e("TestAudioSender", "Streaming Error: ${e.message}")
                "Streaming Error: ${e.message}"
            }
        }
    }

    private fun sendAudioFile(outputStream: OutputStream, audioFile: File) {
        audioFile.inputStream().use { inputStream ->
            inputStream.copyTo(outputStream)
        }
        Log.d("TestAudioSender", "Test audio file sent successfully")
    }

    private fun parseAuthDetails(header: String): Map<String, String> {
        return header.removePrefix("Digest ").split(", ").associate {
            val (key, value) = it.split("=")
            key to value.trim('"')
        }
    }

    private fun buildDigestHeader(url: String, authDetails: Map<String, String>, username: String, password: String): String? {
        val realm = authDetails["realm"] ?: return null
        val nonce = authDetails["nonce"] ?: return null
        val qop = authDetails["qop"] ?: "auth"
        val nc = String.format("%08x", 1)
        val cnonce = System.currentTimeMillis().toString(16)

        val ha1 = md5("$username:$realm:$password")
        val ha2 = md5("POST:$url")
        val response = md5("$ha1:$nonce:$nc:$cnonce:$qop:$ha2")

        return "Digest username=\"$username\", realm=\"$realm\", nonce=\"$nonce\", uri=\"$url\", " +
                "qop=$qop, nc=$nc, cnonce=\"$cnonce\", response=\"$response\""
    }

    private fun md5(data: String): String {
        return MessageDigest.getInstance("MD5").digest(data.toByteArray()).joinToString("") {
            "%02x".format(it)
        }
    }
}