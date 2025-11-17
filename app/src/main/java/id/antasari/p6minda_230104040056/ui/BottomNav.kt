package id.antasari.p6minda_230104040056.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import id.antasari.p6minda_230104040056.ui.navigation.Routes

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomItems = listOf(
    BottomNavItem(Routes.HOME,     "Home", Icons.Filled.Home),
    BottomNavItem(Routes.CALENDAR, "Calendar", Icons.Filled.CalendarMonth),
    BottomNavItem(Routes.INSIGHTS, "Insights", Icons.Filled.BarChart),
    BottomNavItem(Routes.SETTINGS, "Settings", Icons.Filled.Settings),
)

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (homeItem, calendarItem, insightsItem, settingsItem) = bottomItems

        // HOME - geser ke kanan
        BottomNavButton(
            item = homeItem,
            selected = currentRoute == homeItem.route,
            onClick = {
                if (currentRoute != homeItem.route) {
                    navController.navigate(homeItem.route) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier.weight(1f).padding(start = 10.dp)
        )

        // CALENDAR - geser ke kanan
        BottomNavButton(
            item = calendarItem,
            selected = currentRoute == calendarItem.route,
            onClick = {
                if (currentRoute != calendarItem.route) {
                    navController.navigate(calendarItem.route) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier.weight(1f).padding(start = 10.dp)
        )

        // SLOT TENGAH - ruang FAB
        Spacer(Modifier.weight(1f))

        // INSIGHTS - geser ke kiri
        BottomNavButton(
            item = insightsItem,
            selected = currentRoute == insightsItem.route,
            onClick = {
                if (currentRoute != insightsItem.route) {
                    navController.navigate(insightsItem.route) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier.weight(1f).padding(end = 10.dp)
        )

        // SETTINGS - geser ke kiri
        BottomNavButton(
            item = settingsItem,
            selected = currentRoute == settingsItem.route,
            onClick = {
                if (currentRoute != settingsItem.route) {
                    navController.navigate(settingsItem.route) {
                        popUpTo(Routes.HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier.weight(1f).padding(end = 10.dp)
        )
    }
}

@Composable
private fun BottomNavButton(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val activeColor = Color(0xFF6750A4) // MD3 purple
    val inactiveColor = colors.onSurfaceVariant

    val iconColor = if (selected) activeColor else inactiveColor
    val textColor = if (selected) activeColor else inactiveColor

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp).clickable(onClick = onClick),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = item.icon, contentDescription = item.label, tint = iconColor
            )
            Spacer(Modifier.height(0.dp)) // Jarak ikon-teks
            Text(text = item.label, fontSize = 11.sp, fontWeight = FontWeight.Normal, color = textColor)
        }
    }
}