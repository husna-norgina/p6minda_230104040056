package id.antasari.p6minda_230104040056.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPrefsRepository(private val context: Context) {

    companion object {
        private val KEY_USER_NAME: Preferences.Key<String> =
            stringPreferencesKey("user_name")

        // FLAG BARU: onboarding selesai
        private val KEY_ONBOARD_DONE: Preferences.Key<Boolean> =
            booleanPreferencesKey("onboarding_completed")
    }

    // ==== Expose Flows ====
    val userNameFlow: Flow<String?> = context.dataStore.data.map { it[KEY_USER_NAME] }
    val onboardingCompletedFlow: Flow<Boolean> =
        context.dataStore.data.map { it[KEY_ONBOARD_DONE] ?: false }

    // ==== Write APIs ====
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { it[KEY_USER_NAME] = name }
    }

    suspend fun setOnboardingCompleted(done: Boolean) {
        context.dataStore.edit { it[KEY_ONBOARD_DONE] = done }
    }

    // (opsional) reset untuk debugging
    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}