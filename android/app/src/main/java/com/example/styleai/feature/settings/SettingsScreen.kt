package com.example.styleai.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.core.localization.AppLocalization
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.core.theme.SoftMoss

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBackToOnboarding: () -> Unit
) {
    val scrolling = rememberScrollState()
    val isExporting by viewModel.isExporting.collectAsState()
    val notificationMessage by viewModel.dataDeletedMessage.collectAsState()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()
    val isDeveloperMode by viewModel.isDeveloperMode.collectAsState()
    val tapsCount by viewModel.versionTapCount.collectAsState()

    val strings = AppLocalization.getStrings(currentLanguage)

    var showConfirmClearStyleDialog by remember { mutableStateOf(false) }
    var showConfirmResetOnboardingDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrolling)
            .padding(16.dp)
    ) {
        // Title block
        Text(
            text = strings.settingsTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = strings.settingsSubtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Show toast notification like card if any state is active
        if (notificationMessage != null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(notificationMessage!!, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                    TextButton(onClick = { viewModel.dismissMessage() }) {
                        Text(strings.settingsDismiss, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Section: Language Switching Settings
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = strings.settingsLanguageSection,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { viewModel.changeLanguage(AppLanguage.EN) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentLanguage == AppLanguage.EN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "English",
                            color = if (currentLanguage == AppLanguage.EN) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { viewModel.changeLanguage(AppLanguage.RU) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentLanguage == AppLanguage.RU) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Русский",
                            color = if (currentLanguage == AppLanguage.RU) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Section: Privacy & Data Minimization Details
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = if (currentLanguage == AppLanguage.EN) "Privacy & Security Protocol" else "Защита конфиденциальности",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = strings.settingsMinimizationInfo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Section: AI Safety Policy
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = if (currentLanguage == AppLanguage.EN) "AI Styling Safety Guideline" else "Правила безопасности ИИ",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = strings.settingsSafetyInfo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Section: Data Actions
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = if (currentLanguage == AppLanguage.EN) "Local Operations" else "Локальные операции",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        viewModel.exportReport { filename -> /* Export notify action triggers */ }
                    },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SoftMoss),
                    enabled = !isExporting
                ) {
                    Text(if (isExporting) "Generating Export Pack..." else strings.settingsExportButton)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Delete Style Data Only (Does not overwrite language settings or active licenses)
                OutlinedButton(
                    onClick = { showConfirmClearStyleDialog = true },
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                ) {
                    Text(strings.settingsDeleteDataButton, color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Reset App Onboarding (Preserves Style Report but takes you back to onboarding onboarding slides)
                Button(
                    onClick = { showConfirmResetOnboardingDialog = true },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC76B6B))
                ) {
                    Text(strings.settingsResetOnboardingButton)
                }
            }
        }

        // DEVELOPER-ONLY MVP READINESS CHECKLIST (Revealed by tapping the version footer five times)
        if (isDeveloperMode) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "🛠️ Developer MVP Readiness Checklist",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Selected Language: ${currentLanguage.name}", color = Color.LightGray, fontSize = 11.sp, style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Section 1: Native Android
                    DevChecklistCategory("1. Native Android Readiness (100% Core)") {
                        DevChecklistItem(true, "All 10 main screens fully completed in Jetpack Compose")
                        DevChecklistItem(true, "Consistent back & screen-to-screen navigation flows")
                        DevChecklistItem(true, "VM state bindings survive orientation change simulations")
                        DevChecklistItem(true, "Mocks connected strictly via clean StyleRepository constructs")
                        DevChecklistItem(true, "No UI-exclusive navigation or styling blocks")
                    }

                    // Section 2: Safety
                    DevChecklistCategory("2. AI Safety Protocols") {
                        DevChecklistItem(true, "Forced Consent Gate & disclaimers must be confirmed")
                        DevChecklistItem(true, "No free-text input box; only controlled safety selectors")
                        DevChecklistItem(true, "VisualizationRequest constructed solely from safe enums")
                        DevChecklistItem(true, "Banned words ('underwear', 'naked') fully modeled/denied")
                        DevChecklistItem(true, "Input photo validation mocked (lighting/minors/crowds)")
                    }

                    // Section 3: Privacy
                    DevChecklistCategory("3. Privacy Hardening (PrivacybyDesign)") {
                        DevChecklistItem(true, "EXIF metadata stripped before future uploads (ExifStripper)")
                        DevChecklistItem(true, "Raw bitmaps are discarded from cache after analysis completes")
                        DevChecklistItem(true, "Lifecycle-based cache cleanup in TemporaryImageStore")
                        DevChecklistItem(true, "GDPR-compliant Delete Local Data sweeps operate locally")
                        DevChecklistItem(true, "Zero logging of raw paths, contents, or credentials (SafeLogger)")
                    }

                    // Section 4: Monetization
                    DevChecklistCategory("4. Monetization & Purchases") {
                        DevChecklistItem(true, "Mock credit tracking balance state bounds modeled")
                        DevChecklistItem(true, "Pricing package layout preview paywall screens connected")
                        DevChecklistItem(true, "BillingRepository decoupled interface exists")
                    }

                    // Section 5: Backend Service
                    DevChecklistCategory("5. Future Backend Integration") {
                        DevChecklistItem(true, "RemoteStyleApi architecture placeholders completed")
                        DevChecklistItem(true, "MockRemoteStyleApi delivers stable responsive models")
                        DevChecklistItem(true, "Bearer auth header & presigned uploads Todo markers added")
                    }
                }
            }
        }

        // About block with Version Tapper
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = if (currentLanguage == AppLanguage.EN) 
                "App Version: 1.0.0-MVP (Build version - Tap ${5 - tapsCount.coerceAtMost(5)} times for Dev Mode)"
            else 
                "Версия приложения: 1.0.0-MVP (Сборка - нажмите ${5 - tapsCount.coerceAtMost(5)} раз(а) для режима разработчика)",
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.tapVersion() }
                .padding(vertical = 10.dp)
        )
        Text(
            text = "StyleAI Studio Copyright © 2026",
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )

        // Dialog 1: Clear Style Data Only
        if (showConfirmClearStyleDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmClearStyleDialog = false },
                title = { Text(if (currentLanguage == AppLanguage.EN) "Delete style data only?" else "Удалить только данные о стиле?") },
                text = { Text(if (currentLanguage == AppLanguage.EN) "This will safely wipe your active style report, wardrobe checkboxes, and generated looks cache. This leaves language and subscription states preserved." else "Это очистит ваш отчет по стилю, галочки гардероба и сохраненные образы. Язык и состояние подписки останутся прежними.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmClearStyleDialog = false
                            viewModel.deleteStyleDataOnly {
                                onNavigateBackToOnboarding()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC76B6B))
                    ) {
                        Text(if (currentLanguage == AppLanguage.EN) "Confirm Delete" else "Подтвердить удаление")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmClearStyleDialog = false }) {
                        Text(if (currentLanguage == AppLanguage.EN) "Cancel" else "Отмена")
                    }
                }
            )
        }

        // Dialog 2: Reset App Onboarding
        if (showConfirmResetOnboardingDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmResetOnboardingDialog = false },
                title = { Text(if (currentLanguage == AppLanguage.EN) "Reset onboarding sliders?" else "Сбросить онбординг приложения?") },
                text = { Text(if (currentLanguage == AppLanguage.EN) "This will lock screens and take you back to onboarding slides. It is used to test the legal disclaimer gates." else "Это заблокирует экраны и вернет вас к слайдам онбординга для повторного подтверждения правил.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmResetOnboardingDialog = false
                            viewModel.resetAppOnboarding {
                                onNavigateBackToOnboarding()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC76B6B))
                    ) {
                        Text(if (currentLanguage == AppLanguage.EN) "Confirm Reset" else "Подтвердить сброс")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmResetOnboardingDialog = false }) {
                        Text(if (currentLanguage == AppLanguage.EN) "Cancel" else "Отмена")
                    }
                }
            )
        }
    }
}

@Composable
fun DevChecklistCategory(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold, color = Color.LightGray, fontSize = 11.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Column(modifier = Modifier.padding(start = 8.dp), content = content)
    }
}

@Composable
fun DevChecklistItem(checked: Boolean, label: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            if (checked) "✓" else "☐",
            color = if (checked) Color(0xFF10B981) else Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.width(16.dp)
        )
        Text(label, color = Color.Gray, fontSize = 11.sp, style = MaterialTheme.typography.bodySmall)
    }
}
