package sheridan.adolfo_david_romero.prototype_device.ui.videostreaming

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-31
 **/
import android.content.Context
import android.widget.FrameLayout
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RTSPStreamingScreen(rtspUrl1: String, rtspUrl2: String) {
    val context = LocalContext.current

    // Initialize ExoPlayer for both RTSP streams
    val player1 = remember { SimpleExoPlayer.Builder(context).build() }
    val player2 = remember { SimpleExoPlayer.Builder(context).build() }

    DisposableEffect(Unit) {
        onDispose {
            player1.release()
            player2.release()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("RTSP Streaming from Two Cameras", style = MaterialTheme.typography.headlineSmall)

        // Player view for the first RTSP stream
        VideoPlayer(player = player1, url = rtspUrl1)

        Spacer(modifier = Modifier.height(8.dp))

        // Player view for the second RTSP stream
        VideoPlayer(player = player2, url = rtspUrl2)
    }
}

@Composable
fun VideoPlayer(player: SimpleExoPlayer, url: String) {
    val context = LocalContext.current

    // Launch RTSP stream on composition
    LaunchedEffect(url) {
        initializePlayer(context, player, url)
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                this.player = player
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    300 // Adjust height to suit
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // Set height to display videos side by side
    )
}

fun initializePlayer(context: Context, player: SimpleExoPlayer, url: String) {
    val mediaItem = MediaItem.fromUri(url)
    player.setMediaItem(mediaItem)
    player.prepare()
    player.playWhenReady = true
}