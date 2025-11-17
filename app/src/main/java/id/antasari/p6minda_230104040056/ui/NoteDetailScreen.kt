package id.antasari.p6minda_230104040056.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import id.antasari.p6minda_230104040056.data.DiaryEntry
import id.antasari.p6minda_230104040056.data.DiaryRepository
import id.antasari.p6minda_230104040056.data.MindaDatabase
import id.antasari.p6minda_230104040056.util.formatTimestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    entryId: Int,
    onBack: () -> Unit,
    onDeleted: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val context = LocalContext.current
    val db = remember { MindaDatabase.getInstance(context) }
    val repo = remember { DiaryRepository(db.diaryDao()) }

    var entry by remember { mutableStateOf<DiaryEntry?>(null) }

    var showMenu by remember { mutableStateOf(false) }
    var showConfirmDelete by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // Load data entry tertentu ketika screen ini dibuka
    LaunchedEffect(entryId) {
        val loaded = withContext(Dispatchers.IO) {
            repo.getById(entryId)
        }
        entry = loaded
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your entry",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Tombol Edit ✎
                    IconButton(
                        onClick = { onEdit(entryId) },
                        enabled = (entry != null)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit"
                        )
                    }

                    // Tombol ... (More)
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More"
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete") },
                                onClick = {
                                    showMenu = false
                                    if (entry != null) {
                                        showConfirmDelete = true
                                    }
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        }
    ) { InnerPadding ->

        val e = entry
        Column(
            modifier = Modifier
                .padding(InnerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (e == null) {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                // Mood + Title
                Text(
                    text = "${e.mood} ${e.title}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                // Timestamp
                Text(
                    text = formatTimestamp(e.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                // Content (isi diary)
                Text(
                    text = e.content,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(Modifier.height(24.dp))

                HorizontalDivider()

                Spacer(Modifier.height(16.dp))

                // Reflection prompts
                Text(
                    text = "Reflection prompts",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "• What did you learn about yourself today?\n" +
                            "• What are you grateful for right now?\n" +
                            "• Is there something you want to let go?",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    // Dialog konfirmasi Delete
    if (showConfirmDelete && entry != null) {
        AlertDialog(
            onDismissRequest = { showConfirmDelete = false },
            title = { Text("Delete entry?") },
            text = {
                Text(
                    "This action can not be undone. " +
                            "Are you sure you want to delete this diary entry?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val toDelete = entry!!
                        scope.launch(Dispatchers.IO) {
                            repo.remove(toDelete)
                            withContext(Dispatchers.Main) {
                                showConfirmDelete = false
                                onDeleted() // kembali (popBackStack) ke Home
                            }
                        }
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDelete = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
