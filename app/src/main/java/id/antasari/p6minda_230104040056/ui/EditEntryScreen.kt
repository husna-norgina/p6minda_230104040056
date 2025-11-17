package id.antasari.p6minda_230104040056.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.antasari.p6minda_230104040056.data.DiaryEntry
import id.antasari.p6minda_230104040056.data.DiaryRepository
import id.antasari.p6minda_230104040056.data.MindaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEntryScreen(
    entryId: Int,
    onBack: () -> Unit,
    onSaved: (Int) -> Unit
) {
    val context = LocalContext.current
    val db = remember { MindaDatabase.getInstance(context) }
    val repo = remember { DiaryRepository(db.diaryDao()) }
    val scope = rememberCoroutineScope()

    var entry by remember { mutableStateOf<DiaryEntry?>(null) }

    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }

    val moodOptions = remember {
        listOf(
            "😄" to "Happy",
            "🙂" to "Calm",
            "😢" to "Sad",
            "😡" to "Angry",
            "😴" to "Tired",
            "😎" to "Cool"
        )
    }

    var selectedMood by remember { mutableStateOf("😀") }

    var selectedDate by remember { mutableStateOf(java.time.LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(java.time.LocalTime.now().withSecond(0).withNano(0)) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember {
        DateTimeFormatter.ofPattern("d MMM, yyyy", Locale.getDefault())
    }
    val timeFormatter12h = remember {
        DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
    }

    fun formattedDate(): String = selectedDate.format(dateFormatter)
    fun formattedTime(): String = selectedTime.format(timeFormatter12h)

    fun combinedMillis(): Long {
        val dt = LocalDateTime.of(selectedDate, selectedTime)
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    LaunchedEffect(entryId) {
            val loaded = withContext(Dispatchers.IO) {repo.getById(entryId) }
            entry = loaded
            if (loaded != null) {
                titleText = loaded.title
                contentText = loaded.content
                selectedMood = loaded.mood.ifBlank { "🙂" }
                val zdt = Instant.ofEpochMilli(loaded.timestamp).atZone(java.time.ZoneId.systemDefault())
                selectedDate = zdt.toLocalDate()
                selectedTime = zdt.toLocalTime().withSecond(0).withNano(0)
            }

    }

    fun doSave() {
        val current = entry ?: return
        if (titleText.isBlank() || contentText.isBlank()) return

        scope.launch(Dispatchers.IO) {
            val updated = current.copy(
                title = titleText,
                content = contentText,
                mood = selectedMood,
                timestamp = combinedMillis()
            )
            repo.edit(updated)
            withContext(Dispatchers.Main) { onSaved(entryId) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit entry",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = { doSave() },
                        enabled = titleText.isNotBlank() && contentText.isNotBlank(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) { Text("Done") }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Tanggal & Jam
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = formattedDate(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.clickable { showDatePicker = true }
                    )
                    Text(
                        text = ", ", style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formattedTime(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.clickable { showTimePicker = true }
                    )
                }

                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = contentText,
                    onValueChange = { contentText = it },
                    label = { Text("What's on your mind?") },
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(min = 160.dp),
                    singleLine = false,
                    maxLines = 8
                )

                Spacer(Modifier.height(16.dp))

                // Mood 2 baris
                Text(
                    text = "Mood",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))

                val row1 = remember { moodOptions.take(3) }
                val row2 = remember { moodOptions.drop(3).take(3) }

                MoodRow(
                    options = row1,
                    selected = selectedMood,
                    onSelect = { selectedMood = it }
                )

                Spacer(Modifier.height(8.dp))

                MoodRow(
                    options = row2,
                    selected = selectedMood,
                    onSelect = { selectedMood = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f)
                ) { Text("Cancel") }

                Button(
                    onClick = { doSave() },
                    enabled = titleText.isNotBlank() && contentText.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) { Text("Save") }
            }
        }
    }

    // DatePicker
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate
                .atStartOfDay(java.time.ZoneId.systemDefault())
                .toInstant().toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    val ms = datePickerState.selectedDateMillis
                    if (ms != null) {
                        selectedDate = Instant.ofEpochMilli(ms)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("Save") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    // TimePicker (12h)
    if (showTimePicker) {
        val timeState = rememberTimePickerState(
            initialHour = selectedTime.hour,
            initialMinute = selectedTime.minute,
            is24Hour = false
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                Button(onClick = {
                    val h = timeState.hour
                    val m = timeState.minute
                    selectedTime = selectedTime.withHour(h).withMinute(m)
                    showTimePicker = false
                }) { Text("Save") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showTimePicker = false }) { Text("Cancel") }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    TimePicker(state = timeState)
                }
            }
        )
    }
}

/* Reuse komponen & util warna */
@Composable
private fun MoodRow(
    options: List<Pair<String, String>>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { (emoji, label) ->
            val color = moodColor(emoji)
            FilterChip(
                selected = selected == emoji,
                onClick = { onSelect(emoji) },
                label = { Text("$emoji  $label") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = color.copy(alpha = 0.20f),
                    selectedLabelColor = color
                )
            )
        }
    }
}

@Composable
private fun moodColor(mood: String): Color {
    return when (mood.lowercase(Locale.ROOT)) {
        "😄", "happy"  -> Color(0xFF4CAF50) // hijau
        "🙂", "calm"   -> Color(0xFF42A5F5) // biru
        "😢", "sad"    -> Color(0xFFEF5350) // merah
        "😡", "angry"  -> Color(0xFFFF7043) // oranye
        "😴", "tired"  -> Color(0xFFAB47BC) // ungu
        "😎", "cool"   -> Color(0xFF26A6A9) // teal
        else           -> MaterialTheme.colorScheme.primary
    }
}


