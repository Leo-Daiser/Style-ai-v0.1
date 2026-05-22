package com.example.styleai.feature.report

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.model.*
import com.example.styleai.core.localization.AppLocalization
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ReportScreen(
    reportState: StateFlow<StyleReport?>,
    selectedLanguage: StateFlow<AppLanguage>,
    onToggleGapCompleted: (String) -> Unit
) {
    val report by reportState.collectAsState()
    val currentLanguage by selectedLanguage.collectAsState()
    val strings = AppLocalization.getStrings(currentLanguage)
    val scrollState = rememberScrollState()

    if (report == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    val activeReport = report!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Report Header
        Text(
            text = strings.reportTitle,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = if (currentLanguage == AppLanguage.EN) {
                "Refining structure, proportion, and seasonal pigments securely in-memory."
            } else {
                "Анализ структуры, пропорций и сезонного цветотипа выполнен в оперативной памяти."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Section A: Personal Color Palette
        PaletteSection(palette = activeReport.colorPalette, currentLanguage = currentLanguage, strings = strings)

        Spacer(modifier = Modifier.height(20.dp))

        // Section B: Best Silhouettes
        SilhouettesSection(silhouettes = activeReport.silhouettes, currentLanguage = currentLanguage, strings = strings)

        Spacer(modifier = Modifier.height(20.dp))

        // Section C: Style Directions
        StyleDirectionsSection(directions = activeReport.styleDirections, strings = strings)

        Spacer(modifier = Modifier.height(20.dp))

        // Section D: Wardrobe Gaps Checklist
        GapsChecklistSection(gaps = activeReport.wardrobeGaps, onToggleComplete = onToggleGapCompleted, strings = strings)

        Spacer(modifier = Modifier.height(20.dp))

        // Section E: Prioritized Shopping Checklist
        ShoppingListSection(items = activeReport.shoppingList, strings = strings)

        Spacer(modifier = Modifier.height(20.dp))

        // Section F: What to Avoid (Careful, non-body-shaming language)
        AvoidSection(avoids = activeReport.whatToAvoid, strings = strings)

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun PaletteSection(palette: ColorPalette, currentLanguage: AppLanguage, strings: com.example.styleai.core.localization.AppStrings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${strings.reportPaletteSection}: ${palette.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = palette.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Palette Swatches (6 swatches)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                palette.swatchColors.take(6).forEach { hexColor ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(android.graphics.Color.parseColor(hexColor)))
                            .border(1.dp, Color.Black.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Priorities list
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    val toneMatchesLabel = if (currentLanguage == AppLanguage.EN) "Prioritize Tone Matches:" else "Приоритетные тона:"
                    Text(text = toneMatchesLabel, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                    palette.priorityColors.forEach { item ->
                        Text("• $item", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    val carefullyLabel = if (currentLanguage == AppLanguage.EN) "Incorporate Carefully:" else "С осторожностью:"
                    Text(text = carefullyLabel, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    palette.avoidColors.forEach { item ->
                        Text("• $item", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SilhouettesSection(silhouettes: List<SilhouetteRecommendation>, currentLanguage: AppLanguage, strings: com.example.styleai.core.localization.AppStrings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = strings.reportSilhouettesSection,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (currentLanguage == AppLanguage.EN) {
                    "Proportional recommendations suited for balanced geometric alignments:"
                } else {
                    "Пропорциональные рекомендации для сбалансированной геометрии:"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            silhouettes.forEach { recom ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(top = 4.dp)
                            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(5.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(recom.title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                        Text(recom.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun StyleDirectionsSection(directions: List<StyleDirection>, strings: com.example.styleai.core.localization.AppStrings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = strings.reportDirectionsSection,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            directions.forEach { direction ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(direction.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(direction.description, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Best Contexts: ${direction.bestUseCases}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Items: ${direction.recommendedItems.joinToString(", ")}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun GapsChecklistSection(gaps: List<WardrobeGap>, onToggleComplete: (String) -> Unit, strings: com.example.styleai.core.localization.AppStrings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = strings.reportGapsSection,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            gaps.forEach { gap ->
                var isChecked by remember { mutableStateOf(gap.isAcquired) }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = it
                            onToggleComplete(gap.id)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(gap.itemName, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                        Text("${gap.importance} • ${gap.description}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListSection(items: List<ShoppingItem>, strings: com.example.styleai.core.localization.AppStrings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = strings.reportShoppingSection,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            items.forEach { shop ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${shop.priority}. ${shop.title}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text(shop.stylingNotes, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(
                        text = shop.expectedPriceRange,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AvoidSection(avoids: List<String>, strings: com.example.styleai.core.localization.AppStrings) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = strings.reportAvoidSection,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = strings.reportBodySafetyNotice,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            avoids.forEach { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text("•", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp))
                    Text(item, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
    }
}
