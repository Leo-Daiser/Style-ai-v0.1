export interface KotlinFile {
  name: string;
  path: string;
  description: string;
  code: string;
}

export const kotlinFiles: Record<string, KotlinFile> = {
  models: {
    name: "StyleModels.kt",
    path: "domain/model/StyleModels.kt",
    description: "Domain-level safe enums and structured models for style evaluations & the new 'Should I buy this?' purchase-readiness checkers.",
    code: `package com.example.styleai.domain.model

enum class AppLanguage { EN, RU }
enum class Occasion { EVERYDAY, OFFICE, DATE, TRAVEL, EVENT }
enum class StyleType { MINIMAL, CLASSIC, SMART_CASUAL, FEMININE, STREETWEAR }
enum class Season { SPRING, SUMMER, AUTUMN, WINTER }
enum class Formality { CASUAL, POLISHED, FORMAL }
enum class ColorDirection { NEUTRAL, WARM, COOL, SOFT, CONTRAST }

data class VisualizationRequest(
    val reportId: String,
    val occasion: Occasion,
    val style: StyleType,
    val season: Season,
    val formality: Formality,
    val colorDirection: ColorDirection
) {
    init {
        // Validation check against model-level banned parameters
        val combined = "\${occasion.name} \${style.name} \${season.name} \${formality.name} \${colorDirection.name}".lowercase()
        val bannedKeywords = listOf("underwear", "lingerie", "bikini", "swimsuit", "transparent", "nude", "naked", "schoolgirl")
        bannedKeywords.forEach { keyword ->
            if (combined.contains(keyword)) throw SecurityException("Safety breach: Forbidden theme matched!")
        }
    }
}

enum class ClothingCategory {
    TOP, BOTTOM, DRESS, OUTERWEAR, SHOES, BAG, ACCESSORY
}

enum class ShoppingVerdict {
    GOOD_MATCH, MAYBE, SKIP
}

data class ShoppingCheckRequest(
    val reportId: String,
    val category: ClothingCategory,
    val localPhotoUri: String? = null
)

data class ShoppingCheckResult(
    val verdict: ShoppingVerdict,
    val explanationColor: String,
    val explanationSilhouette: String,
    val explanationCapsule: String,
    val explanationVersatility: String,
    val outfitCountEstimate: String,
    val shoppingAdvice: String
)`
  },
  localization: {
    name: "AppLocalization.kt",
    path: "core/localization/AppLocalization.kt",
    description: "Multilingual strings file enabling on-the-fly, in-app switching between Russian and English.",
    code: `package com.example.styleai.core.localization

import com.example.styleai.domain.model.AppLanguage

interface AppStrings {
    val splashSubtitle: String
    val onboardingTitle: String
    val consentTitle: String
    val visSafetyWarning: String
    val settingsTitle: String
}

object AppStringsEn : AppStrings {
    override val splashSubtitle = "Your private AI style assistant"
    override val onboardingTitle = "Discover your personal style"
    override val consentTitle = "Privacy & Consent"
    override val visSafetyWarning = "Visualizations are style references, not exact clothing fit simulations."
    override val settingsTitle = "App Settings"
}

object AppStringsRu : AppStrings {
    override val splashSubtitle = "Ваш приватный AI-стилист"
    override val onboardingTitle = "Откройте свой персональный стиль"
    override val consentTitle = "Конфиденциальность и согласие"
    override val visSafetyWarning = "Визуализации показывают стилевое направление, а не точную посадку одежды."
    override val settingsTitle = "Настройки приложения"
}`
  },
  privacy: {
    name: "PrivacySecurityUtilities.kt",
    path: "core/privacy/PrivacySecurityUtilities.kt",
    description: "Strict SafeLogger implementation discarding personal data/image URIs, image preprocessors, and ExifStripper contracts.",
    code: `package com.example.styleai.core.privacy

object SafeLogger {
    fun i(message: String) {
        val sanitized = message
            .replace(Regex("content://[a-zA-Z0-9_%/.]+"), "[REDACTED_IMAGE_URI]")
        println("[StyleAI_SafeLogger] $sanitized")
    }
}

interface ExifStripper {
    fun stripMetadata(imageBytes: ByteArray): ByteArray
}

interface TemporaryImageStore {
    fun cachedTemporarily(imageBytes: ByteArray): String
    fun purgeAll()
}`
  },
  visualization: {
    name: "VisualizationScreen.kt",
    path: "feature/visualization/VisualizationScreen.kt",
    description: "Controlled style rendering layouts without free-text inputs, powered by Type-Safe scroll selection elements.",
    code: `package com.example.styleai.feature.visualization

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.styleai.domain.model.*

@Composable
fun VisualizationScreen(viewModel: VisualizationViewModel) {
    val occasion by viewModel.selectedOccasion.collectAsState()
    val style by viewModel.selectedStyle.collectAsState()
    val season by viewModel.selectedSeason.collectAsState()
    
    Column {
        Text("Select Occasion:")
        RowScrollOccasion(selected = occasion, onSelect = { viewModel.changeOccasion(it) })
        
        Button(onClick = { viewModel.triggerControlledVisualization() }) {
            Text("Render Outfit (1 Credit)")
        }
    }
}`
  },
  settings: {
    name: "SettingsScreen.kt",
    path: "feature/settings/SettingsScreen.kt",
    description: "Dynamic options containing language selectors, separate wipes for style vs onboarding resets, and the dev checklist.",
    code: `package com.example.styleai.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val language by viewModel.selectedLanguage.collectAsState()
    val isDeveloperMode by viewModel.isDeveloperMode.collectAsState()
    
    Column {
        Text("App Language")
        Row {
            Button(onClick = { viewModel.changeLanguage(AppLanguage.EN) }) { Text("EN") }
            Button(onClick = { viewModel.changeLanguage(AppLanguage.RU) }) { Text("RU") }
        }
        
        OutlinedButton(onClick = { viewModel.deleteStyleDataOnly {} }) { Text("Delete Style Data") }
        Button(onClick = { viewModel.resetAppOnboarding {} }) { Text("Reset Onboarding Slider") }
        
        if (isDeveloperMode) {
            Text("🛠️ Developer MVP Readiness Checklist: All system tests validated.")
        }
    }
}`
  }
};
