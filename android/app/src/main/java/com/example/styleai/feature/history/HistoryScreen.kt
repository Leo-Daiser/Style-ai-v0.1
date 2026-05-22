package com.example.styleai.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.model.HistoryItem
import com.example.styleai.domain.model.SavedLook
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onNavigateBackToReport: () -> Unit
) {
    val histories by viewModel.historyItems.collectAsState()
    val looks by viewModel.savedLooks.collectAsState()

    var activeTabIdx by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Your Local Cache History",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Cached data stored on device. Raw imagery is skipped in logs.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Custom local Tab Indicator
        TabRow(selectedTabIndex = activeTabIdx) {
            Tab(selected = activeTabIdx == 0, onClick = { activeTabIdx = 0 }) {
                Text("Saved Reports (${histories.size})", modifier = Modifier.padding(vertical = 12.dp), fontSize = 13.sp)
            }
            Tab(selected = activeTabIdx == 1, onClick = { activeTabIdx = 1 }) {
                Text("Saved Looks (${looks.size})", modifier = Modifier.padding(vertical = 12.dp), fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (activeTabIdx == 0) {
            if (histories.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("No saved reports found. Create one by scanning your profile details.", textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(histories) { historyItem ->
                        HistoryItemCard(
                            item = historyItem,
                            onLoadReport = onNavigateBackToReport,
                            onDelete = { viewModel.deleteHistoryItem(historyItem.id) }
                        )
                    }
                }
            }
        } else {
            if (looks.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("No saved looks yet. Highlight looks in visualization grids to check them out here.", textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(looks) { savedLook ->
                        SavedLookItemCard(
                            look = savedLook,
                            onDelete = { viewModel.removeSavedLook(savedLook.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(
    item: HistoryItem,
    onLoadReport: () -> Unit,
    onDelete: () -> Unit
) {
    val formatter = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
    val dateString = formatter.format(Date(item.date))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(item.reportTitle, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                TextButton(onClick = onDelete) {
                    Text("Delete", color = Color(0xFFC76B6B), fontSize = 11.sp)
                }
            }
            Text("Date: $dateString", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text("Assigned Color season: ${item.paletteName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Saved Looks inside: ${item.numSavedLooks}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                Button(
                    onClick = onLoadReport,
                    modifier = Modifier.height(30.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp)
                ) {
                    Text("Load Report", fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun SavedLookItemCard(
    look: SavedLook,
    onDelete: () -> Unit
) {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val dateString = formatter.format(Date(look.savedAt))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Visual Swatch indicator on side
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(android.graphics.Color.parseColor(look.imagePlaceholderHex)), shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(look.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                Text("Added: $dateString", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            IconButton(onClick = onDelete) {
                Text("✕", color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
