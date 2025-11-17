package id.antasari.p6minda_230104040056.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_entries")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,        // contoh: "Gratitude Journal"
    val content: String,      // contoh: "What am I thankful for today? ..."
    val mood: String,         // contoh: "😎 Happy", "😢 Sad"
    val timestamp: Long       // System.currentTimeMillis()
)
