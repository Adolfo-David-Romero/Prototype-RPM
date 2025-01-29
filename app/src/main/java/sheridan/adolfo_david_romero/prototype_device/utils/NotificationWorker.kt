package sheridan.adolfo_david_romero.prototype_device.utils

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 **/
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import sheridan.adolfo_david_romero.prototype_device.R


class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val medicationName = inputData.getString("name") ?: "Medication"
        val medicationTime = inputData.getString("time") ?: "Time"

        createNotificationChannel()
        sendNotification(medicationName, medicationTime)

        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Medication Reminder Channel"
            val descriptionText = "Channel for Medication Reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("medication_reminders", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(medicationName: String, medicationTime: String) {
        val builder = NotificationCompat.Builder(context, "medication_reminders")
            .setSmallIcon(R.drawable.notif) // Ensure this icon exists in res/drawable
            .setContentTitle("Medication Reminder")
            .setContentText("$medicationName at $medicationTime")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(1001, builder.build())
        }
    }
}
/*
fun scheduleNotification(context: Context, medication: MedicationReminder) {
    val data = workDataOf(
        "name" to medication.name,
        "time" to medication.time
    )

    val delay = calculateDelayForMedication(medication.time) // A function to calculate delay in milliseconds based on the medication time.
    val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .setInputData(data)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}

// Example function to calculate the delay
fun calculateDelayForMedication(time: String): Long {
    // Implement logic to convert time (e.g., "08:00 AM") to the appropriate delay in milliseconds.
    return 0L // Placeholder for the delay calculation.
}*/
