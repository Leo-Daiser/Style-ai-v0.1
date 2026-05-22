package com.example.styleai.feature.paywall

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
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.core.localization.AppLocalization

@Composable
fun PaywallScreen(
    viewModel: PaywallViewModel,
    onDismiss: () -> Unit
) {
    val currentLanguage by viewModel.selectedLanguage.collectAsState()
    val balance by viewModel.creditBalance.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val strings = AppLocalization.getStrings(currentLanguage)
    val scrollState = rememberScrollState()
    var selectedPlanId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState) {
        if (uiState is PaywallUiState.Success) {
            // Unselect plan
            selectedPlanId = null
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            // Back/Dismiss Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.paywallTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Text("✕", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = strings.paywallSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Current Account State
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = if (currentLanguage == AppLanguage.EN) "Current Balance" else "Текущий баланс",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${balance.totalCredits} ${if (currentLanguage == AppLanguage.EN) "Rendering Credits" else "Кредитов рендеринга"}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (balance.isProUser) MaterialTheme.colorScheme.primary else Color.Gray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (balance.isProUser) "PRO ACTIVE" else "FREE",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (currentLanguage == AppLanguage.EN) "Select Pricing Package" else "Выберите подходящий пакет",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Pricing Options List
            viewModel.getPurchasePlans().forEach { plan ->
                val isSelected = selectedPlanId == plan.id
                val localizedPlanTitle = when(plan.id) {
                    "plan_full_report" -> strings.paywallCompleteReport
                    "plan_pro_sub" -> strings.paywallSubPlan
                    "pack_10" -> strings.paywallCreditsPack
                    "pack_30" -> if (currentLanguage == AppLanguage.EN) "Buy 30 Outfit Credits ($9.99)" else "Купить 30 кредитов образов ($9.99)"
                    "pack_100" -> if (currentLanguage == AppLanguage.EN) "Buy 100 Outfit Credits ($24.99)" else "Купить 100 кредитов образов ($24.99)"
                    else -> plan.name
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { selectedPlanId = plan.id },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = localizedPlanTitle,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = plan.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedPlanId = plan.id }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    selectedPlanId?.let { viewModel.purchasePlan(it) }
                },
                enabled = selectedPlanId != null && uiState !is PaywallUiState.Processing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(strings.paywallButton, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sandbox Payment Disclaimer
            Text(
                text = if (currentLanguage == AppLanguage.EN) {
                    "This is an MVP Sandbox Checkout template. Making purchases simulates valid credit balance debitings locally without charge."
                } else {
                    "Это демонстрационный шаблон кассы MVP. Покупка симулирует начисление баланса кредитов без реального списания средств."
                },
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Processing Overlay / Notification
        when (val state = uiState) {
            is PaywallUiState.Processing -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable(enabled = false) {},
                    contentAlignment = Alignment.Center
                ) {
                    Card {
                        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(if (currentLanguage == AppLanguage.EN) "Verifying Receipt Store..." else "Проверка квитанции магазина...")
                        }
                    }
                }
            }
            is PaywallUiState.Success -> {
                AlertDialog(
                    onDismissRequest = { viewModel.resetState() },
                    confirmButton = {
                        Button(onClick = { viewModel.resetState() }) {
                            Text("OK")
                        }
                    },
                    title = { Text(if (currentLanguage == AppLanguage.EN) "Success" else "Успешно") },
                    text = { Text(state.message) }
                )
            }
            is PaywallUiState.Error -> {
                AlertDialog(
                    onDismissRequest = { viewModel.resetState() },
                    confirmButton = {
                        Button(onClick = { viewModel.resetState() }) {
                            Text(if (currentLanguage == AppLanguage.EN) "Close" else "Закрыть")
                        }
                    },
                    title = { Text(if (currentLanguage == AppLanguage.EN) "Billing Error" else "Ошибка оплаты") },
                    text = { Text(state.error) }
                )
            }
            else -> {}
        }
    }
}
