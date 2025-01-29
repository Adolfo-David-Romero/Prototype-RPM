package sheridan.adolfo_david_romero.prototype_device.domain.audiostreaming
/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-27
 **/

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import kotlin.experimental.xor
import kotlin.math.log2

@SuppressLint("MissingPermission")
object AudioStreaming {
    private const val SAMPLE_RATE = 8000  // Sampling rate required for G.711A
    private val BUFFER_SIZE = AudioRecord.getMinBufferSize(
        SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT
    )

    private var audioRecord: AudioRecord? = null
    private var isStreaming = false

    private fun initializeAudioRecord(): AudioRecord? {
        return AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            BUFFER_SIZE
        ).also {
            if (it.state != AudioRecord.STATE_INITIALIZED) {
                Log.e("AudioStreaming", "AudioRecord failed to initialize")
                return null
            }
        }
    }

    suspend fun startStreaming(username: String, password: String, cameraIp: String): String {

        // Configure camera audio settings first
        val isConfigured = configureCameraAudio(cameraIp, username, password)
        if (!isConfigured) {
            return "Failed to configure camera audio settings"
        }

        // Proceed with initializing AudioRecord and streaming as usual
        audioRecord = initializeAudioRecord()
        if (audioRecord == null) return "Failed to initialize AudioRecord."

        audioRecord?.startRecording()
        isStreaming = true

        val client = OkHttpClient()
        val url = "http://$cameraIp/cgi-bin/audio.cgi?action=postAudio&httptype=singlepart&channel=1"

        return withContext(Dispatchers.IO) {
            try {
                // Authenticate using Digest
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

                        connection.outputStream.use { outputStream ->
                            streamAudioData(outputStream)
                        }
                        connection.disconnect()
                        return@withContext "Audio streaming to camera started"
                    } else {
                        return@withContext "Authentication header missing in 401 response"
                    }
                }
                "Failed to authenticate"
            } catch (e: Exception) {
                Log.e("AudioStreaming", "Streaming Error: ${e.message}")
                "Streaming Error: ${e.message}"
            } finally {
                stopStreaming()
            }
        }
    }

    fun stopStreaming() {
        isStreaming = false
        audioRecord?.let { record ->
            if (record.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                try {
                    record.stop()
                    Log.d("AudioStreaming", "AudioRecord stopped successfully")
                } catch (e: IllegalStateException) {
                    Log.e("AudioStreaming", "Error stopping AudioRecord: ${e.message}")
                }
            } else {
                Log.d("AudioStreaming", "AudioRecord was not recording when stop was called")
            }
            record.release()
        }
        audioRecord = null
        Log.d("AudioStreaming", "Stopped streaming and released resources")
    }
    fun configureCameraAudio(cameraIp: String, username: String, password: String): Boolean {
        val client = OkHttpClient()
        val configUrl = "http://$cameraIp/cgi-bin/configManager.cgi?action=setConfig&Audio.Compression=G.711A"
        val initialRequest = Request.Builder().url(configUrl).build()
        val initialResponse = client.newCall(initialRequest).execute()

        if (initialResponse.code == 401) {
            val authHeader = initialResponse.header("WWW-Authenticate")
            if (authHeader != null) {
                val authDetails = parseAuthDetails(authHeader)
                val authorizationHeader = buildDigestHeader(configUrl, authDetails, username, password)

                val connection = URL(configUrl).openConnection() as HttpURLConnection
                connection.apply {
                    requestMethod = "GET"
                    setRequestProperty("Authorization", authorizationHeader)
                }
                return connection.responseCode == 200
            }
        }
        return false
    }

    private fun streamAudioData(outputStream: OutputStream) {
        val buffer = ShortArray(BUFFER_SIZE / 2)
        val g711aBuffer = ByteArray(BUFFER_SIZE / 2)

        while (isStreaming && audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
            val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
            if (read > 0) {
                // Convert each PCM sample to G.711A format
                for (i in 0 until read) {
                    g711aBuffer[i] = pcmToG711A(buffer[i])
                }
                outputStream.write(g711aBuffer, 0, read)
                outputStream.flush()
            }
        }
    }

    private fun pcmToG711A(pcm: Short): Byte {
        val mask = 0xD5.toByte()
        var pcmSample = pcm.toInt()
        val sign = (pcmSample shr 8) and 0x80
        if (sign != 0) pcmSample = -pcmSample
        if (pcmSample > 0x7FFF) pcmSample = 0x7FFF
        pcmSample += 0x84
        val exponent = (log2(pcmSample.toDouble()) - 7).toInt()
        val mantissa = (pcmSample shr (exponent + 3)) and 0x0F
        val alaw = (sign or (exponent shl 4) or mantissa).toByte()
        return alaw xor mask
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