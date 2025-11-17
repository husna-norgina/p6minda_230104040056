package id.antasari.p6minda_230104040056.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

// ONBOARDING
import id.antasari.p6minda_230104040056.ui.WelcomeScreen
import id.antasari.p6minda_230104040056.ui.AskNameScreen
import id.antasari.p6minda_230104040056.ui.HelloScreen
import id.antasari.p6minda_230104040056.ui.StartJournalingScreen

// MAIN
import id.antasari.p6minda_230104040056.ui.HomeScreen
import id.antasari.p6minda_230104040056.ui.calendar.CalendarScreen
import id.antasari.p6minda_230104040056.ui.InsightsScreen
import id.antasari.p6minda_230104040056.ui.SettingsScreen
import id.antasari.p6minda_230104040056.ui.NewEntryScreen
import id.antasari.p6minda_230104040056.ui.NoteDetailScreen
import id.antasari.p6minda_230104040056.ui.EditEntryScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    storedName: String?,
    hasCompletedOnboarding: Boolean,
    onSaveUserName: (String) -> Unit,
    onSetOnboardingCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Mulai berdasarkan FLAG onboarding
    val start = if (hasCompletedOnboarding) Routes.HOME else Routes.ONBOARD_WELCOME

    NavHost(
        navController = navController,
        startDestination = start,
        modifier = modifier
    ) {
        // ===== ONBOARDING =====
        composable(Routes.ONBOARD_WELCOME) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Routes.ONBOARD_ASKNAME) },
                onLoginRestore = { navController.navigate(Routes.ONBOARD_ASKNAME) }
            )
        }

        composable(Routes.ONBOARD_ASKNAME) {
            AskNameScreen(
                onConfirm = { typed ->
                    onSaveUserName(typed)
                    navController.navigate(Routes.ONBOARD_HELLO)
                },
                onSkip = { navController.navigate(Routes.ONBOARD_HELLO) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ONBOARD_HELLO) {
            HelloScreen(
                userName = storedName ?: "",
                onNext = { navController.navigate(Routes.ONBOARD_CTA) }
            )
        }
        composable(Routes.ONBOARD_CTA) {
            StartJournalingScreen(
                onGotIt = {
                    onSetOnboardingCompleted()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARD_WELCOME) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // ===== MAIN =====
        composable(Routes.HOME) {
            HomeScreen(
                userName = storedName,
                onOpenEntry = { id -> navController.navigate("detail/$id") }
            )
        }

        composable(Routes.CALENDAR) { CalendarScreen(onEdit = { id -> navController.navigate("edit/$id") }) }
        composable(Routes.INSIGHTS) { InsightsScreen() }
        composable(Routes.SETTINGS) { SettingsScreen(userName = storedName) }

        // ===== ENTRY FLOW =====
        composable(Routes.NEW) {
            NewEntryScreen(
                onBack = { navController.popBackStack() },
                onSaved = { newId ->
                    navController.popBackStack()
                    navController.navigate("detail/$newId") { launchSingleTop = true }
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("entryId") { type = NavType.IntType })
        ) { back ->
            val id = back.arguments?.getInt("entryId") ?: -1
            NoteDetailScreen(
                entryId = id,
                onBack = { navController.popBackStack() },
                onDeleted = { navController.popBackStack() },
                onEdit = { eid -> navController.navigate("edit/$eid") }
            )
        }

        composable(
            route = Routes.EDIT,
            arguments = listOf(navArgument("entryId") { type = NavType.IntType })
        ) { back ->
            val id = back.arguments?.getInt("entryId") ?: -1
            EditEntryScreen(
                entryId = id,
                onBack = { navController.popBackStack() },
                onSaved = { savedId ->
                    navController.popBackStack()
                    navController.navigate("detail/$savedId") { launchSingleTop = true }
                }
            )
        }
    }
}

