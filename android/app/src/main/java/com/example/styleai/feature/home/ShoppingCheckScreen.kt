package com.example.styleai.feature.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.core.localization.AppLocalization
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.ClothingCategory
import com.example.styleai.domain.model.ShoppingVerdict
import com.example.styleai.domain.model.ShoppingCheckResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ShoppingCheckScreen(
    currentLanguageState: StateFlow<AppLanguage>,
    onNavigateBack: () -> Unit
) {
    val currentLanguage by currentLanguageState.collectAsState()
    val strings = AppLocalization.getStrings(currentLanguage)
    val scrollState = rememberScrollState()

    var selectedCategory by remember { mutableStateOf(ClothingCategory.TOP) }
    var itemColorDescriptor by remember { mutableStateOf("") }
    
    var isAnalyzing by remember { mutableStateOf(false) }
    var analysisResult by remember { mutableStateOf<ShoppingCheckResult?>(null) }
    var analysisStepText by remember { mutableStateOf("") }

    val categories = ClothingCategory.values()

    LaunchedEffect(isAnalyzing) {
        if (isAnalyzing) {
            val stepsEn = listOf(
                "Isolating garment parameters...",
                "Cross-referencing active color palette...",
                "Cross-referencing silhouette rules...",
                "Estimating capsule pairing combinations...",
                "Formulating compatibility verdict..."
            )
            val stepsRu = listOf(
                "Выделение характеристик вещи...",
                "Сопоставление с вашей цветовой палитрой...",
                "Проверка соответствия линиям силуэта...",
                "Оценка сочетаемости с текущей капсулой...",
                "Формирование вердикта совместимости..."
            )
            val steps = if (currentLanguage == AppLanguage.RU) stepsRu else stepsEn
            for (step in steps) {
                analysisStepText = step
                delay(800)
            }
            
            // Generate mock result
            val verdicts = listOf(ShoppingVerdict.GOOD_MATCH, ShoppingVerdict.MAYBE, ShoppingVerdict.SKIP)
            val selectedVerdict = verdicts[Math.abs(selectedCategory.name.hashCode()) % verdicts.size]

            analysisResult = if (currentLanguage == AppLanguage.RU) {
                ShoppingCheckResult(
                    verdict = selectedVerdict,
                    explanationColor = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Великолепное соответствие вашей осенней палитре. Тон идеально гармонирует."
                        ShoppingVerdict.MAYBE -> "Приемлемый оттенок, но рекомендуется выбирать более насыщенные тона."
                        ShoppingVerdict.SKIP -> "Цвет конфликтует с вашей палитрой. Сделает цвет лица бледным."
                    },
                    explanationSilhouette = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Крой поддерживает рекомендованный силуэт и линию плеч."
                        ShoppingVerdict.MAYBE -> "Посадка нейтральная, но может потребовать структурного пояса."
                        ShoppingVerdict.SKIP -> "Нарушает баланс пропорций. Избегайте этого типа воротников."
                    },
                    explanationCapsule = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Отлично закрывает текущие пробелы вашей капсулы."
                        ShoppingVerdict.MAYBE -> "Добавит разнообразия, хотя не является первой необходимостью."
                        ShoppingVerdict.SKIP -> "Не впишется в текущий гардероб без докупки дополнительных вещей."
                    },
                    explanationVersatility = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Высокая сочетаемость. Подходит под 4 базовых комплекта."
                        ShoppingVerdict.MAYBE -> "Средняя универсальность. Будет надеваться избирательно."
                        ShoppingVerdict.SKIP -> "Будет лежать без дела. Низкая сочетаемость."
                    },
                    outfitCountEstimate = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "5+ образов готово"
                        ShoppingVerdict.MAYBE -> "2-3 образа готово"
                        ShoppingVerdict.SKIP -> "0-1 образ готов"
                    },
                    shoppingAdvice = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Рекомендуется к покупке. Вещь станет ключевой инвестицией в капсулу текущего сезона."
                        ShoppingVerdict.MAYBE -> "Покупайте только если влюблены в вещь и готовы достроить её аксессуарами."
                        ShoppingVerdict.SKIP -> "Рекомендуем пропустить. Поищите альтернативу в более теплой цветовой гамме."
                    }
                )
            } else {
                ShoppingCheckResult(
                    verdict = selectedVerdict,
                    explanationColor = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Fits beautifully into your Autumn tone recommendations. Brings out natural pigment highlights."
                        ShoppingVerdict.MAYBE -> "Neutral tone, but would be more vibrant if shifted towards richer earth palettes."
                        ShoppingVerdict.SKIP -> "Washes out your complexion. Outside of recommended seasonal vectors."
                    },
                    explanationSilhouette = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Correct structured lines. Accentuates vertical frames elegantly."
                        ShoppingVerdict.MAYBE -> "Relaxed fit, might require tailoring or styled blazers to align properly."
                        ShoppingVerdict.SKIP -> "Draws attention away from vertical geometry. Competing layout volumes."
                    },
                    explanationCapsule = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Perfectly matches existing capsule lists and closes high-priority gaps."
                        ShoppingVerdict.MAYBE -> "Good standalone option, but doesn't directly address immediate list priorities."
                        ShoppingVerdict.SKIP -> "Difficult to pair with current pieces without buying secondary garments."
                    },
                    explanationVersatility = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Excellent compatibility factor with 4+ ready outfits."
                        ShoppingVerdict.MAYBE -> "Moderate versatility index. Standard pair options."
                        ShoppingVerdict.SKIP -> "Isolated wardrobe layout. High friction pairing."
                    },
                    outfitCountEstimate = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "5+ Combinations ready"
                        ShoppingVerdict.MAYBE -> "2-3 Combinations ready"
                        ShoppingVerdict.SKIP -> "0-1 Combinations"
                    },
                    shoppingAdvice = when (selectedVerdict) {
                        ShoppingVerdict.GOOD_MATCH -> "Highly Recommended. A top investment for structured capsules."
                        ShoppingVerdict.MAYBE -> "Acceptable if bought at a deep discount, but prepare secondary layers to combine."
                        ShoppingVerdict.SKIP -> "A pass recommendation. Look for warmer shades or alternative shoulders."
                    }
                )
            }
            isAnalyzing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header Back button
        TextButton(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("← ${strings.shopBtnBack}", fontWeight = FontWeight.Bold)
        }

        Text(
            text = strings.shopTitle,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = strings.shopSubtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (!isAnalyzing && analysisResult == null) {
            // Configuration mode
            Text(
                text = strings.shopCategoryLabel,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Category select grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(categories) { cat ->
                        val isSel = cat == selectedCategory
                        Card(
                            onClick = { selectedCategory = cat },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cat.name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Simulated Local Photo Input
            Text(
                text = strings.shopPhotoLabel,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                    .clickable {
                        itemColorDescriptor = "Beige Corduroy Jacket"
                    },
                contentAlignment = Alignment.Center
            ) {
                if (itemColorDescriptor.isEmpty()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📷", fontSize = 28.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.EN) "Tap to select sample garment sketch" else "Коснитесь для симуляции фото вещи",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🧥", fontSize = 28.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Garment: $itemColorDescriptor (Selected)",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { isAnalyzing = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(strings.shopBtnAnalyze, fontWeight = FontWeight.Bold)
            }
        } else if (isAnalyzing) {
            // Analyzing status loader
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = analysisStepText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Complete Result screen has finished
            val res = analysisResult!!
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = strings.shopResultTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    // Verdict Indicator Capsule
                    val verdictColor = when (res.verdict) {
                        ShoppingVerdict.GOOD_MATCH -> Color(0xFF2E7D32)
                        ShoppingVerdict.MAYBE -> Color(0xFFEF6C00)
                        ShoppingVerdict.SKIP -> Color(0xFFC62828)
                    }
                    val verdictText = when (res.verdict) {
                        ShoppingVerdict.GOOD_MATCH -> strings.shopVerdictGood
                        ShoppingVerdict.MAYBE -> strings.shopVerdictMaybe
                        ShoppingVerdict.SKIP -> strings.shopVerdictSkip
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(verdictColor)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = verdictText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    ResultField(title = strings.shopSectionColor, value = res.explanationColor)
                    ResultField(title = strings.shopSectionSilhouette, value = res.explanationSilhouette)
                    ResultField(title = strings.shopSectionCapsule, value = res.explanationCapsule)
                    ResultField(title = strings.shopSectionVersatility, value = res.explanationVersatility)
                    ResultField(title = strings.shopSectionOutfits, value = res.outfitCountEstimate)

                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))

                    Text(
                        text = strings.shopSectionAdvice,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = res.shoppingAdvice,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Button(
                onClick = { analysisResult = null },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = if (currentLanguage == AppLanguage.EN) "Check another item" else "Проверить другую вещь", fontWeight = FontWeight.Bold)
            }
        }
    }
}
