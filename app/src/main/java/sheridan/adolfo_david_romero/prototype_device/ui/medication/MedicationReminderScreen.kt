package sheridan.adolfo_david_romero.prototype_device.ui.medication

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 **/
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import java.util.*


// --- Shared MedicationReminder Data Class ---
data class MedicationReminder(
    val id: String = "",
    val name: String = "",
    val time: String = "",
    val dosage: String = "",
    val isTaken: Boolean = false
)

// --- ViewModel for User App ---
class MedicationViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _medications = MutableStateFlow<List<MedicationReminder>>(emptyList())
    val medications: StateFlow<List<MedicationReminder>> = _medications

    fun fetchMedications() {
        db.collection("medications")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _medications.value = emptyList()
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    _medications.value = snapshot.documents.mapNotNull { it.toObject(MedicationReminder::class.java) }
                }
            }
    }

    fun scheduleAlarms(context: Context) {
        medications.value.forEach { reminder ->
            scheduleMedicationAlarm(context, reminder)
        }
    }
}

// --- Scheduling Medication Alarms ---
@SuppressLint("MissingPermission", "ScheduleExactAlarm")
fun scheduleMedicationAlarm(context: Context, reminder: MedicationReminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MedicationAlarmReceiver::class.java).apply {
        putExtra("name", reminder.name)
        putExtra("time", reminder.time)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val calendar = Calendar.getInstance()

    try {
        calendar.time = timeFormat.parse(reminder.time) ?: Date()
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
}

// --- Alarm Receiver for Notifications ---
class MedicationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val name = intent?.getStringExtra("name")
        val time = intent?.getStringExtra("time")
        Toast.makeText(context, "Time to take your medication: $name at $time", Toast.LENGTH_LONG).show()
    }
}

// --- Medication Screen for Nurse App ---
@Composable
fun MedicationReminderScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var medicationList by remember { mutableStateOf(listOf<MedicationReminder>()) }
    var newMedicationName by remember { mutableStateOf("") }
    var newMedicationTime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.collection("medications")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    medicationList = emptyList()
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    medicationList = snapshot.documents.mapNotNull { it.toObject(MedicationReminder::class.java) }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Medication Reminders",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newMedicationName,
            onValueChange = { newMedicationName = it },
            label = { Text("Medication Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = newMedicationTime,
            onValueChange = { newMedicationTime = it },
            label = { Text("Time (e.g., 08:00 AM)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (newMedicationName.isNotBlank() && newMedicationTime.isNotBlank()) {
                    val newMedication = MedicationReminder(
                        id = UUID.randomUUID().toString(),
                        name = newMedicationName,
                        time = newMedicationTime
                    )
                    db.collection("medications").document(newMedication.id).set(newMedication)
                    newMedicationName = ""
                    newMedicationTime = ""
                }
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Medication")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(medicationList) { medication ->
                MedicationCard(
                    medication = medication,
                    onDelete = {
                        db.collection("medications").document(medication.id).delete()
                    }
                )
            }
        }
    }
}

@Composable
fun MedicationCard(medication: MedicationReminder, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = medication.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Time: ${medication.time}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier.clickable { onDelete() },
                tint = Color.Red
            )
        }
    }
}