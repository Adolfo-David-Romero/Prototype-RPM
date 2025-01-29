package sheridan.adolfo_david_romero.prototype_device.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-11-28
 **/


class AlertListener(private val context: Context) {

    private val db = FirebaseFirestore.getInstance()

    fun startListening() {
        db.collection("alerts")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    println("Error listening to alerts: $error")
                    return@addSnapshotListener
                }

                processAlerts(snapshots)
            }
    }

    private fun processAlerts(snapshots: QuerySnapshot?) {
        snapshots?.documentChanges?.forEach { change ->
            if (change.type == com.google.firebase.firestore.DocumentChange.Type.ADDED) {
                val alert = change.document.data
                showNotification(alert)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(alert: Map<String, Any>) {
        val channelId = "fall_alerts"
        val title = "Fall Detected"
        val message = "Time: ${alert["time"]}\nLocation: ${alert["latitude"]}, ${alert["longitude"]}"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Fall Alerts", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), notification)
    }
}
