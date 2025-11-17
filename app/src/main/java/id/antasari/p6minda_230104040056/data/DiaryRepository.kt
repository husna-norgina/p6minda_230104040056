package id.antasari.p6minda_230104040056.data

import kotlinx.coroutines.flow.Flow

class DiaryRepository(
    private val dao: DiaryDao
) {
    // Flow untuk Calendar/Home (real-time)
    fun entriesFlow(): Flow<List<DiaryEntry>> = dao.observeAll()

    // READ semua entry (untuk HomeScreen legacy)
    suspend fun allEntries(): List<DiaryEntry> = dao.getAll()

    // READ satu entry by id (untuk Detail & Edit)
    suspend fun getById(id: Int): DiaryEntry? = dao.getById(id)

    // CREATE entry baru
    suspend fun add(entry: DiaryEntry): Long = dao.insert(entry)

    // UPDATE entry
    suspend fun edit(entry: DiaryEntry) = dao.update(entry)

    // DELETE entry
    suspend fun remove(entry: DiaryEntry) = dao.delete(entry)
}