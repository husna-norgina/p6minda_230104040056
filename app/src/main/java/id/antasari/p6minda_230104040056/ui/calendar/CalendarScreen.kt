package id.antasari.p6minda_230104040056.ui.calendar

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.antasari.p6minda_230104040056.data.DiaryEntry
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onEdit: (Int) -> Unit = {}
) {
    val app = LocalContext.current.applicationContext as Application
    val viewModel: CalendarViewModel = viewModel(factory = CalendarViewModel.provideFactory(app))
    val diaryByDate = viewModel.diaryByDate.collectAsStateWithLifecycle(emptyMap()).value

    val today = remember {  LocalDate.now() }
    var visibleMonth by remember { mutableStateOf(YearMonth.of(today.year, today.month)) }
    var selectedDate by remember { mutableStateOf(today) }

    fun goPrevMonth() { visibleMonth = visibleMonth.minusMonths(1); selectedDate = visibleMonth.atDay(1) }
    fun goNextMonth() { visibleMonth = visibleMonth.plusMonths(1); selectedDate = visibleMonth.atDay(1) }

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    // visual constants
    val pagePadding = 16.dp
    val gridSpacing = 4.dp
    val cellHeight = 40.dp
    val portraitGridHeight = 220.dp

    // ====== State untuk picker bulan/tahun/hari di title bar ======
    var showPicker by remember { mutableStateOf(false) }
    val currentYear = LocalDate.now().year
    val monthName = visibleMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() }
    val headerText =
        if (visibleMonth.year == currentYear) monthName else "$monthName ${visibleMonth.year}"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // === Gaya dan ukuran DISAMAKAN dengan judul "Minda" di Home ===
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { showPicker = true }
                    ) {
                        Text(
                            text = headerText,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "▾",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                // padding standar layer + innerPadding dari Scaffold
                .padding(innerPadding)
                .padding(horizontal = pagePadding)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gridSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(
                    DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
                ).forEach { dow ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dow.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                }
            }

            Spacer(Modifier.height(6.dp))
            CurvedDivider(color = MaterialTheme.colorScheme.surfaceVariant, height = 16.dp)

            Spacer(Modifier.height(6.dp))

            val cells = remember(visibleMonth) { buildMonthCells(visibleMonth) }
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                verticalArrangement = Arrangement.spacedBy(gridSpacing),
                horizontalArrangement = Arrangement.spacedBy(gridSpacing),
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (isLandscape) Modifier.weight(1f) else Modifier.height(portraitGridHeight))
                    .pointerInput(visibleMonth) {
                        var acc = 0f
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, drag -> acc += drag },
                            onDragEnd = {
                                val t = 60f
                                if (acc > t) goPrevMonth()
                                if (acc < -t) goNextMonth()
                                    acc = 0f
                            }
                        )
                    }
            ) {
                items(cells) { c ->
                    DayCell(
                        date = c.date,
                        inCurrentMonth = c.inCurrentMonth,
                        selected = c.date == selectedDate,
                        hasDiary = diaryByDate[c.date]?.isNotEmpty() == true,
                        cellHeight = cellHeight,
                        onClick = {
                            selectedDate = c.date
                            visibleMonth = YearMonth.from(c.date)
                        }
                    )
                }
            }

            Spacer(Modifier.height(6.dp))
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (isLandscape) Modifier.weight(1f) else Modifier)
            ) {
                DiaryListForDate(
                    date = selectedDate,
                    entries = diaryByDate[selectedDate].orEmpty(),
                    onEdit = onEdit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    if (showPicker) {
        DayMonthYearPickerDialog(
            initial = selectedDate,
            onDismiss = { showPicker = false },
            onConfirm = { picked ->
                selectedDate = picked
                visibleMonth = YearMonth.from(picked)
                showPicker = false
            }
        )
    }
}

@Composable
fun CurvedDivider(color: Color, height: Dp = 28.dp, curveHeight: Dp = 14.dp) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height
        val curveH = curveHeight.toPx()

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, h - curveH)

            // cubicTo: kiri tinggi → kanan runcing
            cubicTo(
                w * 0.3f, h + curveH * 0.5f,
                w * 0.7f, h - curveH * 0.8f,
                w, h - curveH * 0.1f
            )

            lineTo(w, 0f)
            close()
        }

        drawPath(path, color)
    }
}


/* ==================== Kalender ==================== */

data class DayCellData(val date: LocalDate, val inCurrentMonth: Boolean)

private fun buildMonthCells(ym: YearMonth): List<DayCellData> {
    val first = ym.atDay(1)
    val sundayIndex = first.dayOfWeek.value % 7
    val start = first.minusDays(sundayIndex.toLong())
    return (0 until 42).map { i ->
        val d = start.plusDays(i.toLong())
        DayCellData(d, d.month == ym.month)
    }
}

@Composable
private fun DayCell(
    date: LocalDate,
    inCurrentMonth: Boolean,
    selected: Boolean,
    hasDiary: Boolean,
    cellHeight: Dp,
    onClick: () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val textColor =
        if (inCurrentMonth) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cellHeight)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                Modifier
                    .padding(top = 1.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(primary)
            ) {
                Text(
                    text = date.dayOfMonth.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = onPrimary,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Text(
                text = date.dayOfMonth.toString(),
                modifier = Modifier.padding(top = 2.dp),
                color = textColor,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }

        if (hasDiary) {
            Box(
                Modifier
                    .size(5.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = (-6).dp)
                    .clip(CircleShape)
                    .background(if (selected) onPrimary else primary)
            )
        }
    }
}

/* ==================== List Diary ==================== */
@Composable
private fun DiaryListForDate(
    date: LocalDate,
    entries: List<DiaryEntry>,
    onEdit: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(64.dp)
        ) {
            Text(date.dayOfMonth.toString(), style = MaterialTheme.typography.headlineSmall)
            Text(
                date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                date.year.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(Modifier.weight(1f)) {
            if (entries.isEmpty()) {
                val label = date.format(DateTimeFormatter.ofPattern("d MMM"))
                Text(
                    text = "No diary on $label",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(entries) { e -> DiaryCardItem(e, onEdit) }
                }
            }
        }
    }
}

@Composable
private fun DiaryCardItem(e: DiaryEntry, onEdit: (Int) -> Unit) {
    val timeStr = Instant.ofEpochMilli(e.timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
        .format(DateTimeFormatter.ofPattern("h:mm a"))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onEdit(e.id) } // tap to edit
            .padding(start = 12.dp, top = 0.dp, end = 12.dp, bottom = 12.dp)
    ) {
        Text(
            timeStr,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            e.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(Modifier.height(6.dp))
        if (!e.content.isNullOrBlank()) {
            Text(
                e.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/* ==================== Picker Dialog ==================== */
@Composable
private fun DayMonthYearPickerDialog(
    initial: LocalDate,
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    var pickedYear by remember { mutableStateOf(initial.year) }
    var pickedMonth by remember { mutableStateOf(initial.month) }
    var pickedDay by remember { mutableStateOf(initial.dayOfMonth) }

    fun clampDay() {
        val max = YearMonth.of(pickedYear, pickedMonth).lengthOfMonth()
        if (pickedDay > max) pickedDay = max
        if (pickedDay < 1) pickedDay = 1
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                clampDay()
                onConfirm(LocalDate.of(pickedYear, pickedMonth, pickedDay))
            }) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
        title = { Text("Select date") },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.widthIn(min = 64.dp)) {
                    Text("▲", fontSize = 18.sp, modifier = Modifier.clickable { pickedDay += 1; clampDay() })
                    Spacer(Modifier.height(4.dp))
                    Text("$pickedDay", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text("▼", fontSize = 18.sp, modifier = Modifier.clickable { pickedDay -= 1; clampDay() })
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.widthIn(min = 72.dp)) {
                    Text("▲", fontSize = 18.sp, modifier = Modifier.clickable { pickedMonth = pickedMonth.plus(1); clampDay() })
                    Spacer(Modifier.height(4.dp))
                    Text(pickedMonth.getDisplayName(TextStyle.SHORT, Locale.ENGLISH), style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text("▼", fontSize = 18.sp, modifier = Modifier.clickable { pickedMonth = pickedMonth.minus(1); clampDay() })
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.widthIn(min = 84.dp)) {
                    Text("▲", fontSize = 18.sp, modifier = Modifier.clickable { pickedYear += 1; clampDay() })
                    Spacer(Modifier.height(4.dp))
                    Text(pickedYear.toString(), style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text("▼", fontSize = 18.sp, modifier = Modifier.clickable { pickedYear -= 1; clampDay() })
                }
            }
        }
    )
}