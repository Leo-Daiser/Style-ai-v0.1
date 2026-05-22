package com.example.styleai.feature.upload

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.model.UploadedPhoto
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.core.theme.SoftMoss
import com.example.styleai.core.localization.AppLocalization

@Composable
fun UploadScreen(
    viewModel: UploadViewModel,
    onNavigateToReport: () -> Unit
) {
    val selfie by viewModel.selfiePhoto.collectAsState()
    val fullBody by viewModel.fullBodyPhoto.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()

    val strings = AppLocalization.getStrings(currentLanguage)
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState) {
        if (uiState is UploadUiState.AnalysisSuccess) {
            onNavigateToReport()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UploadUiState.Analyzing -> {
                AnalysisProgressView(
                    stepIndex = state.stepIndex,
                    stepText = when(state.stepIndex) {
                        0 -> strings.loadingStep1
                        1 -> strings.loadingStep2
                        2 -> strings.loadingStep3
                        3 -> strings.loadingStep4
                        4 -> strings.loadingStep5
                        5 -> strings.loadingStep6
                        else -> strings.loadingStep6
                    },
                    percentage = state.percentage,
                    currentLanguage = currentLanguage
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = strings.uploadTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = strings.uploadSubtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Instruction Guidelines
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (currentLanguage == AppLanguage.EN) "Guidelines for Accurate Results:" else "Рекомендации для точных результатов:",
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val instructions = if (currentLanguage == AppLanguage.EN) listOf(
                                "● Pure neutral natural lighting (by a window is best)",
                                "● Neutral facial expression and relaxed flat hands",
                                "● Exactly one person visible in the framing frame",
                                "● Absolute safety: no nudity, underwear, or kids",
                                "● Avoid blurry actions, group templates, or distance filters"
                            ) else listOf(
                                "● Естественное нейтральное освещение (лучше у окна)",
                                "● Нейтральное выражение лица и расслабленные руки",
                                "● Ровно один человек в кадре",
                                "● Абсолютная безопасность: без наготы, белья и детей",
                                "● Избегайте размытых кадров, групповых фото и сильных фильтров"
                            )
                            instructions.forEach { text ->
                                Text(text, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 1.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Selfie Image Selection Box
                    PhotoPickerBox(
                        title = strings.uploadSelfieLabel,
                        uploadedPhoto = selfie,
                        onSelectMockUri = { viewModel.selectSelfie("internal_selfie_good.jpg") },
                        onSelectMockFailureUri = { viewModel.selectSelfie("selfie_dark_blurry.jpg") },
                        onSelectMockMinorUri = { viewModel.selectSelfie("selfie_minor_protected.jpg") },
                        currentLanguage = currentLanguage
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Full Body Image Selection Box
                    PhotoPickerBox(
                        title = strings.uploadFullBodyLabel,
                        uploadedPhoto = fullBody,
                        onSelectMockUri = { viewModel.selectFullBody("internal_fullbody_good.jpg") },
                        onSelectMockFailureUri = { viewModel.selectFullBody("fullbody_dark_blurry.jpg") },
                        onSelectMockMinorUri = { viewModel.selectFullBody("fullbody_crowd_people.jpg") },
                        currentLanguage = currentLanguage
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Render validation warnings or failure messages
                    if (state is UploadUiState.ValidationFailed) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDE8E8)),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                val reasonLabel = if (currentLanguage == AppLanguage.EN) "Rejection Reason" else "Причина отклонения"
                                Text("$reasonLabel: ${state.reason}", color = Color(0xFF9B1C1C), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                                state.warnings.forEach { warn ->
                                    Text(warn, color = Color(0xFF9B1C1C), style = MaterialTheme.typography.bodySmall)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                TextButton(onClick = { viewModel.clearUploads() }) {
                                    Text(
                                        text = if (currentLanguage == AppLanguage.EN) "Clear and Upload Another" else "Очистить и загрузить заново",
                                        color = Color(0xFF9B1C1C),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    if (state is UploadUiState.Error) {
                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    Button(
                        onClick = { viewModel.startAnalysis() },
                        enabled = selfie != null && fullBody != null && state !is UploadUiState.ValidationFailed,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text(strings.uploadButton)
                    }

                    if (selfie != null || fullBody != null) {
                        TextButton(
                            onClick = { viewModel.clearUploads() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(if (currentLanguage == AppLanguage.EN) "Reset All Uploads" else "Сбросить загрузки", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoPickerBox(
    title: String,
    uploadedPhoto: UploadedPhoto?,
    onSelectMockUri: () -> Unit,
    onSelectMockFailureUri: () -> Unit,
    onSelectMockMinorUri: () -> Unit,
    currentLanguage: AppLanguage
) {
    var showTestingSelectionMenu by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 6.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                .clickable { showTestingSelectionMenu = !showTestingSelectionMenu },
            contentAlignment = Alignment.Center
        ) {
            if (uploadedPhoto == null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "+ Select Image" else "+ Выберите изображение",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Click to pick mock state" else "Нажмите для выбора состояния",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                    Text("✓ ${uploadedPhoto.uriString}", color = SoftMoss, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Ready for validation checks" else "Готово к проверке безопасности",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        if (showTestingSelectionMenu && uploadedPhoto == null) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onSelectMockUri(); showTestingSelectionMenu = false }, colors = ButtonDefaults.buttonColors(containerColor = SoftMoss)) {
                    Text(if (currentLanguage == AppLanguage.EN) "Select Safe" else "Безопасное", fontSize = 11.sp)
                }
                Button(onClick = { onSelectMockFailureUri(); showTestingSelectionMenu = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC76B6B))) {
                    Text(if (currentLanguage == AppLanguage.EN) "Select Dark/Blurry" else "Темное/Размытое", fontSize = 11.sp)
                }
                Button(onClick = { onSelectMockMinorUri(); showTestingSelectionMenu = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                    Text(if (currentLanguage == AppLanguage.EN) "Trigger Safety Filter" else "Сработать фильтр", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun AnalysisProgressView(
    stepIndex: Int,
    stepText: String,
    percentage: Int,
    currentLanguage: AppLanguage
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            progress = { percentage / 100f },
            modifier = Modifier.size(90.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 6.dp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = if (currentLanguage == AppLanguage.EN) "Analyzing Profile... $percentage%" else "Анализ профиля... $percentage%",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stepText,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Small indicator checklist tracker
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            val steps = if (currentLanguage == AppLanguage.EN) listOf(
                "Photo safety scan",
                "Color palette assignment",
                "Silhouette outline checks",
                "Style categorization report ready"
            ) else listOf(
                "Проверка безопасности",
                "Анализ цветовой палитры",
                "Геометрия силуэта",
                "Формирование отчета"
            )
            steps.forEachIndexed { idx, label ->
                val opacity = if (idx <= stepIndex / 2) 1.0f else 0.4f
                val checkText = if (idx < stepIndex / 2) "✓" else if (idx == stepIndex / 2) "●" else "○"
                Text(
                    text = "$checkText $label",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = opacity)
                )
            }
        }
    }
}
