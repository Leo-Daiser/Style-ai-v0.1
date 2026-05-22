package com.example.styleai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.*
import com.example.styleai.data.repository.BillingRepositoryImpl
import com.example.styleai.data.repository.StyleRepositoryImpl
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.StyleReport
import com.example.styleai.domain.repository.BillingRepository
import com.example.styleai.domain.repository.StyleRepository
import com.example.styleai.feature.history.HistoryScreen
import com.example.styleai.feature.history.HistoryViewModel
import com.example.styleai.feature.onboarding.*
import com.example.styleai.feature.paywall.PaywallScreen
import com.example.styleai.feature.paywall.PaywallViewModel
import com.example.styleai.feature.report.ReportScreen
import com.example.styleai.feature.settings.SettingsScreen
import com.example.styleai.feature.settings.SettingsViewModel
import com.example.styleai.feature.upload.UploadScreen
import com.example.styleai.feature.upload.UploadViewModel
import com.example.styleai.feature.visualization.VisualizationScreen
import com.example.styleai.feature.visualization.VisualizationViewModel
import com.example.styleai.core.localization.AppLocalization
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainActivity : ComponentActivity() {

    private lateinit var styleRepository: StyleRepository
    private lateinit var billingRepository: BillingRepository

    private lateinit var activeReportState: StateFlow<StyleReport?>
    private lateinit var selectedLanguageState: StateFlow<AppLanguage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize our persistent standard repositories with App Context
        styleRepository = StyleRepositoryImpl(applicationContext)
        billingRepository = BillingRepositoryImpl(applicationContext)

        // State flows from repositories compiled tightly under lifecycleScope
        activeReportState = styleRepository.getActiveReport().stateIn(
            lifecycleScope,
            SharingStarted.Eagerly,
            null
        )
        selectedLanguageState = styleRepository.getSelectedLanguage().stateIn(
            lifecycleScope,
            SharingStarted.Eagerly,
            AppLanguage.EN
        )

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen(
                            onSplashFinished = {
                                // Decide splash routing based on completion status
                                val onboardingCompletedFlow = styleRepository.isOnboardingCompleted()
                                val consentFlow = styleRepository.getConsentState()
                                
                                lifecycleScope.launchWhenStarted {
                                    onboardingCompletedFlow.collect { onboardingCompleted ->
                                        if (!onboardingCompleted) {
                                            navController.navigate("onboarding") {
                                                popUpTo("splash") { inclusive = true }
                                            }
                                        } else {
                                            consentFlow.collect { consent ->
                                                if (!consent.isFullyConsented) {
                                                    navController.navigate("consent") {
                                                        popUpTo("splash") { inclusive = true }
                                                    }
                                                } else {
                                                    navController.navigate("report") {
                                                        popUpTo("splash") { inclusive = true }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }

                    composable("onboarding") {
                        val onboardingViewModel = remember { OnboardingViewModel(styleRepository) }
                        OnboardingScreen(
                            viewModel = onboardingViewModel,
                            onOnboardingFinished = {
                                navController.navigate("consent") {
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("consent") {
                        val onboardingViewModel = remember { OnboardingViewModel(styleRepository) }
                        ConsentScreen(
                            viewModel = onboardingViewModel,
                            onConsentApproved = {
                                navController.navigate("upload") {
                                    popUpTo("consent") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("upload") {
                        val uploadViewModel = remember { UploadViewModel(styleRepository) }
                        UploadScreen(
                            viewModel = uploadViewModel,
                            onNavigateToReport = {
                                navController.navigate("report") {
                                    popUpTo("upload") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("report") {
                        DashboardHostScreen(
                            styleRepository = styleRepository,
                            billingRepository = billingRepository,
                            activeReportState = activeReportState,
                            selectedLanguageState = selectedLanguageState,
                            onNavigateToPaywall = {
                                navController.navigate("paywall")
                            },
                            onResetOnboarding = {
                                navController.navigate("onboarding") {
                                    popUpTo("report") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("paywall") {
                        val paywallViewModel = remember { PaywallViewModel(billingRepository, styleRepository) }
                        PaywallScreen(
                            viewModel = paywallViewModel,
                            onDismiss = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardHostScreen(
    styleRepository: StyleRepository,
    billingRepository: BillingRepository,
    activeReportState: StateFlow<StyleReport?>,
    selectedLanguageState: StateFlow<AppLanguage>,
    onNavigateToPaywall: () -> Unit,
    onResetOnboarding: () -> Unit
) {
    var activeTab by remember { mutableStateOf(0) }
    val currentLanguage by selectedLanguageState.collectAsState()
    val strings = AppLocalization.getStrings(currentLanguage)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    icon = { Text("📋", fontSize = 20.sp) },
                    label = { Text(if (currentLanguage == AppLanguage.EN) "Report" else "Отчет", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    icon = { Text("✨", fontSize = 20.sp) },
                    label = { Text(if (currentLanguage == AppLanguage.EN) "Looks" else "Образы", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = activeTab == 2,
                    onClick = { activeTab = 2 },
                    icon = { Text("🕒", fontSize = 20.sp) },
                    label = { Text(if (currentLanguage == AppLanguage.EN) "History" else "История", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = activeTab == 3,
                    onClick = { activeTab = 3 },
                    icon = { Text("⚙️", fontSize = 20.sp) },
                    label = { Text(if (currentLanguage == AppLanguage.EN) "Settings" else "Настройки", fontSize = 10.sp) }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (activeTab) {
                0 -> {
                    val reportExists by activeReportState.collectAsState()
                    if (reportExists == null) {
                        // If no report has been generated, prompt a helpful indicator to let them go to upload
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                                Text(
                                    text = if (currentLanguage == AppLanguage.EN) "No active styling report found." else "Активный отчет по стилю не найден.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(onClick = onResetOnboarding) {
                                    Text(if (currentLanguage == AppLanguage.EN) "Run Profile Scan" else "Запустить сканирование")
                                }
                            }
                        }
                    } else {
                        ReportScreen(
                            reportState = activeReportState,
                            selectedLanguage = selectedLanguageState,
                            onToggleGapCompleted = { gapId ->
                                // Optional local interactive action for Wardrobe Checklist gaps toggling
                            }
                        )
                    }
                }
                1 -> {
                    val visualizationViewModel = remember {
                        VisualizationViewModel(styleRepository, billingRepository)
                    }
                    VisualizationScreen(
                        viewModel = visualizationViewModel,
                        onNavigateToPaywall = onNavigateToPaywall
                    )
                }
                2 -> {
                    val historyViewModel = remember {
                        HistoryViewModel(styleRepository)
                    }
                    HistoryScreen(
                        viewModel = historyViewModel,
                        onNavigateBackToReport = { activeTab = 0 }
                    )
                }
                3 -> {
                    val settingsViewModel = remember {
                        SettingsViewModel(styleRepository)
                    }
                    SettingsScreen(
                        viewModel = settingsViewModel,
                        onNavigateBackToOnboarding = onResetOnboarding
                    )
                }
            }
        }
    }
}
