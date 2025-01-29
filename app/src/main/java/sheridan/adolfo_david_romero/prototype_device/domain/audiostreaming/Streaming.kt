/*
package sheridan.adolfo_david_romero.prototype_device.domain.audiostreaming

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.internal.closeQuietly
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

*/
/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 **//*

@Composable
fun MultipleRTSPStreams(rtspUrls: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        rtspUrls.forEach { rtspUrl ->
            if (rtspUrl.isNotEmpty()) {
                RTSPVideoStream(rtspUrl = rtspUrl, modifier = Modifier.weight(1f))
            } else {
                Log.e("RTSP", "Empty RTSP URL provided")
            }
        }
    }
}
// Single RTSP stream handler using ExoPlayer
@Composable
fun RTSPVideoStream(rtspUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var player by remember { mutableStateOf<ExoPlayer?>(null) }

    DisposableEffect(rtspUrl) {
        Log.d("RTSPVideoStream", "Setting up stream for $rtspUrl")

        player = ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(rtspUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }

        onDispose {
            Log.d("RTSPVideoStream", "Releasing player for $rtspUrl")
            player?.release()
        }
    }

    AndroidView(
        factory = { PlayerView(context).apply { this.player = player } },
        modifier = modifier.fillMaxSize(),
        update = { it.player = player }
    )
}
// Function to stream audio to the camera speaker
@SuppressLint("MissingPermission")
fun startAudioStream(cameraIp: String, username: String, password: String) {
    // Set up audio recording parameters
    val audioBufferSize = AudioRecord.getMinBufferSize(
        44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT
    )
    val audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
        audioBufferSize
    )

    // Define the camera's HTTP API endpoint for audio
    val url = "http://$cameraIp/cgi-bin/audio.cgi?action=postAudio&httptype=singlepart&channel=1"

    // Create an OkHttp client
    val client = OkHttpClient()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Prepare Basic Authentication
            val credentials = "$username:$password"
            val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

            // Create a streaming POST request
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", basicAuth)
                .addHeader("Content-Type", "audio/g711a") // Adjust according to the audio format you are using
                .post(object : RequestBody() {
                    override fun contentType() = "audio/g711a".toMediaType()

                    override fun writeTo(sink: okio.BufferedSink) {
                        val buffer = ByteArray(audioBufferSize)
                        audioRecord.startRecording()

                        Log.d("AudioStream", "Streaming audio to $cameraIp")

                        // Continuously stream audio while recording
                        while (audioRecord.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                            val read = audioRecord.read(buffer, 0, buffer.size)
                            if (read > 0) {
                                sink.write(buffer, 0, read)
                            }
                        }

                        audioRecord.stop()
                        sink.closeQuietly()
                    }
                })
                .build()

            // Execute the request
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Log.d("AudioStream", "Audio streamed successfully to $cameraIp")
            } else {
                Log.e("AudioStream", "Failed to stream audio: ${response.code}")
            }

            response.closeQuietly()
        } catch (e: Exception) {
            Log.e("AudioStream", "Error streaming audio: ${e.message}", e)
        } finally {
            audioRecord.release()
        }
    }
}

//Possible Alternative
@Composable
fun VLCPlayer(rtspUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val libVLC = remember { LibVLC(context) }
    val mediaPlayer = remember { MediaPlayer(libVLC) }

    DisposableEffect(Unit) {
        val media = Media(libVLC, rtspUrl)
        mediaPlayer.media = media
        media.release()

        mediaPlayer.play()

        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
            libVLC.release()
        }
    }

    AndroidView(
        factory = { VLCVideoLayout(context).apply { mediaPlayer.attachViews(this, null, false, false) } },
        modifier = modifier.fillMaxSize(),
        update = { it.keepScreenOn = true }
    )
}*/
