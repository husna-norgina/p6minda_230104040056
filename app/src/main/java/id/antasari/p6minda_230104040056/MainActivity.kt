package id.antasari.p6minda_230104040056

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.antasari.p6minda_230104040056.data.UserPrefsRepository
import id.antasari.p6minda_230104040056.ui.BottomNavBar
import id.antasari.p6minda_230104040056.ui.navigation.AppNavHost
import id.antasari.p6minda_230104040056.ui.navigation.Routes
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // --- Repo & state (DataStore) ---
                    val userPrefs = remember { UserPrefsRepository(this@MainActivity) }
                    val scope = rememberCoroutineScope()

                    val userName by userPrefs.userNameFlow.collectAsState(initial = null)
                    val onboardDone by userPrefs.onboardingCompletedFlow.collectAsState(initial = false)

                    // --- Nav ---
                    val navController = rememberNavController()
                    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                    Scaffold(
                        bottomBar = {
                            if (shouldShowBottomBar(currentRoute)) {
                                BottomNavBar(navController = navController)
                            }
                        },

                        floatingActionButton = {
                            if (shouldShowBottomBar(currentRoute)) {
                                FloatingActionButton(
                                    onClick = { navController.navigate(Routes.NEW) },
                                    modifier = Modifier.offset(y = 40.dp),
                                    containerColor = Color(0xFF6750A4),
                                    contentColor = Color.White
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "New entry")
                                }
                            }
                        },

                        floatingActionButtonPosition = FabPosition.Center
                    ) { innerPadding ->
                        AppNavHost(
                            navController = navController,
                            storedName = userName,
                            hasCompletedOnboarding = onboardDone,
                            onSaveUserName = { name ->
                                scope.launch { userPrefs.saveUserName(name) }
                            },
                            onSetOnboardingCompleted = {
                                scope.launch { userPrefs.setOnboardingCompleted(true) }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

/** Bottom bar hanya tampil di 4 tab utama. */
private fun shouldShowBottomBar(route: String?): Boolean =
    route in setOf(Routes.HOME, Routes.CALENDAR, Routes.INSIGHTS, Routes.SETTINGS)