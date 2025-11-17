package id.antasari.p6minda_230104040056.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import id.antasari.p6minda_230104040056.data.DiaryEntry
import id.antasari.p6minda_230104040056.data.DiaryRepository
import id.antasari.p6minda_230104040056.data.MindaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TestRoomScreen() {

    val context = LocalContext.current
    val db = remember { MindaDatabase.getInstance(context) }
    val repo = remember { DiaryRepository(db.diaryDao()) }
    val scope = rememberCoroutineScope()

    var lastInsertedId by remember { mutableStateOf<Int?>(null) }
    var fetchedEntry by remember { mutableStateOf<DiaryEntry?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val newEntry = DiaryEntry(
                        id = 0,
                        title = "Test Insert",
                        content = "This is a test entry from TestRoomScreen",
                        mood = "😔",
                        timestamp = System.currentTimeMillis()
                    )
                    val newId = repo.add(newEntry) // returns Long
                    withContext(Dispatchers.Main) {
                        lastInsertedId = newId.toInt()
                    }
                }
            }
        ) {
            Text("Insert Sample Entry")
        }

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val idToLoad = lastInsertedId
                    val loaded = if (idToLoad != null) {
                        repo.getById(idToLoad)
                    } else null
                    withContext(Dispatchers.Main) {
                        fetchedEntry = loaded
                    }
                }
            },
            enabled = lastInsertedId != null
        ) {
            Text("Load Last Inserted Entry")
        }

        Text(text = "LastInsertedId: ${lastInsertedId ?: "-"}")

        Text(
            text = buildString {
                append("FetchedEntry:\n")
                val e = fetchedEntry
                if (e != null) {
                    append("id=${e.id}\n")
                    append("title=${e.title}\n")
                    append("mood=${e.mood}\n")
                    append("content=${e.content.take(50)} ...\n")
                } else {
                    append("null")
                }
            }
        )
    }
}
