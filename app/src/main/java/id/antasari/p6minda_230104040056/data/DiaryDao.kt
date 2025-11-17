package id.antasari.p6minda_230104040056.data

import androidx.room.*

@Dao
interface DiaryDao {

    @Query("SELECT * FROM diary_entries ORDER BY timestamp DESC")
    suspend fun getAll(): List<DiaryEntry>

    @Query("SELECT * FROM diary_entries ORDER BY timestamp DESC")
    fun observeAll(): kotlinx.coroutines.flow.Flow<List<DiaryEntry>>

    @Query("SELECT * FROM diary_entries WHERE id = :entryId LIMIT 1")
    suspend fun getById(entryId: Int): DiaryEntry?

    @Insert
    suspend fun insert(entry: DiaryEntry): Long

    @Update
    suspend fun update(entry: DiaryEntry)

    @Delete
    suspend fun delete(entry: DiaryEntry)
}
