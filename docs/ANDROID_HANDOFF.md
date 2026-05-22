# Android Handoff Specification

This document provides a comprehensive blueprint for native developers porting the StyleAI UX Prototype into Kotlin + Jetpack Compose with strict adherence to architectural standards.

---

## 🗂️ Recommended Package Structure

The native app should be structured using standard **Clean Architecture** and MVVM patterns:

```
com.example.styleai/
│
├── core/
│   ├── di/                 # Hilt Modules
│   ├── localization/       # Multilingual state engines (EN & RU resources)
│   └── theme/              # Material Design 3 and Custom earth-cardboard styles
│
├── data/
│   ├── cache/              # Jetpack DataStore (User settings, language selection, and Consent keys)
│   ├── database/           # Room Database (Wardrobe list index, saved decisions history, style metrics)
│   └── repository/         # Local-only repo implementations (no network latency or remote syncs)
│
├── domain/
│   ├── model/              # Pure domain representations: WardrobeItem, ShoppingCheckResult, Outfit
│   └── usecase/            # Use cases: GetWardrobeItemsUseCase, EvaluateShoppingCheckUseCase
│
└── presentation/
    ├── home/               # HomeScreen.kt, HomeViewModel.kt
    ├── wardrobe/           # WardrobeListScreen.kt, WardrobeItemDetailScreen.kt, WardrobeViewModel.kt
    ├── decisions/          # DecisionsHistoryScreen.kt, DecisionsViewModel.kt
    ├── looks/              # LooksInspirationScreen.kt, LooksViewModel.kt
    ├── profile/            # StyleProfileScreen.kt (Profile rules tab), ProfileViewModel.kt
    └── navigation/         # NavGraph.kt (Compose Destinations with 5 navigation backstack tabs)
```

---

## 🧭 Navigation Route Contracts

Use safe routing definitions utilizing sealed destinations or Navigation Compose routes:

```kotlin
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Consent : Screen("consent")
    
    // Core Bottom-Navigation Tabs
    object Home : Screen("home")
    object Wardrobe : Screen("wardrobe")
    object Decisions : Screen("decisions")
    object Looks : Screen("looks")
    object Profile : Screen("profile")
    
    // Deeper Stack Screen Flows
    object ShoppingCheck : Screen("shopping_check")
    object ItemDetail : Screen("item_detail/{itemId}") {
        fun createRoute(itemId: String) = "item_detail/$itemId"
    }
    object Paywall : Screen("paywall")
}
```

---

## 📊 Core Domain Models

### 1. Wardrobe Item Entity
```kotlin
data class WardrobeItem(
    val id: String,
    val name: String,
    val category: String, // "Top", "Bottom", "Dress", "Outerwear", "Shoes", "Bag", "Accessory"
    val color: String,
    val colorDir: String, // "Neutral", "Warm", "Cool", "Soft", "Contrast"
    val season: String,
    val versatilityScore: Int, // Calculated internally (20 - 99)
    val outfitsCount: Int,
    val rarelyWorn: Boolean,
    val isWishlist: Boolean,
    val imageEmoji: String, // Symbolic visual, native drawable icons mapped in Compose
    val hexColor: String
)
```

### 2. Shopping Evaluation Parameters
```kotlin
enum class ClothingCategory { TOP, BOTTOM, DRESS, OUTERWEAR, SHOES, BAG, ACCESSORY }
enum class ColorDirection { NEUTRAL, WARM, COOL, SOFT, CONTRAST }
enum class ShoppingVerdict { GOOD_MATCH, MAYBE, SKIP }

data class ShoppingCheckResult(
    val verdict: ShoppingVerdict,
    val explanationColor: String,
    val explanationSilhouette: String,
    val explanationCapsule: String,
    val explanationVersatility: String,
    val outfitCountEstimate: String,
    val shoppingAdvice: String
)
```

---

## ⚙️ Deterministic Evaluation Logic (Kotlin Engine)

The core style business logic runs in-memory offline without remote API keys or network latency. Port the simulator's evaluation engine using this exact Kotlin mapping:

```kotlin
fun evaluateShoppingResult(
    category: ClothingCategory,
    context: String, // Context selection from: "Basic", "Trend", "Occasion", "Similar", "Unsure"
    colorDir: ColorDirection,
    hasReport: Boolean
): ShoppingCheckResult {
    var verdict = ShoppingVerdict.MAYBE
    
    // Strict decision trees
    val isSimilar = context == "Similar" || context == "Похожая вещь"
    val isLowVersatility = context == "Unsure" || context == "Малополезная вещь" || context == "LowVersatility"
    
    if (isSimilar || isLowVersatility) {
        verdict = ShoppingVerdict.SKIP
    } else if ((context == "Basic" || context == "Базовая вещь") && 
               (colorDir == ColorDirection.NEUTRAL || colorDir == ColorDirection.WARM || colorDir == ColorDirection.SOFT)) {
        verdict = ShoppingVerdict.GOOD_MATCH
    } else if (context == "Trend" || context == "Тренд сезона") {
        verdict = ShoppingVerdict.MAYBE
    } else if (context == "Occasion" || context == "Особый случай") {
         verdict = if (colorDir == ColorDirection.COOL || colorDir == ColorDirection.CONTRAST) {
             ShoppingVerdict.SKIP
         } else {
             ShoppingVerdict.MAYBE
         }
    }

    // Accessory exceptions and structural shortcuts
    val isAccessoryCat = category == ClothingCategory.SHOES || 
                          category == ClothingCategory.BAG || 
                          category == ClothingCategory.ACCESSORY
    if (isAccessoryCat && colorDir == ColorDirection.NEUTRAL) {
        verdict = if (isSimilar) ShoppingVerdict.SKIP else ShoppingVerdict.GOOD_MATCH
    }

    // Localized string responses matching selected user locale
    return ShoppingCheckResult(
        verdict = verdict,
        explanationColor = when(verdict) {
            ShoppingVerdict.GOOD_MATCH -> if (hasReport) "Color Synergy: Aligns with your Soft Autumn guide." else "Color Synergy: Neutral/soft tones provide base matching versatility."
            ShoppingVerdict.MAYBE -> "Color Check: Vivid chromatic tone requiring specific surrounding layering."
            ShoppingVerdict.SKIP -> "Color Clash: High physical vibration contrast that disrupts visual alignment."
        },
        explanationSilhouette = "Silhouette Fit: Relaxed column structures match natural posture geometry.",
        explanationCapsule = when(verdict) {
            ShoppingVerdict.GOOD_MATCH -> "Capsule Utility: Fills high-priority structural gaps in wardrobe checklists."
            ShoppingVerdict.MAYBE -> "Capsule Utility: Medium priority item. Ensure a real void exists."
            ShoppingVerdict.SKIP -> "Capsule Utility: Redundant item. Risk of adding clutter."
        },
        explanationVersatility = when(verdict) {
            ShoppingVerdict.GOOD_MATCH -> "Versatility: Coordinates seamlessly with tailoring or standard outerwear."
            ShoppingVerdict.MAYBE -> "Versatility: Restricted combine capacity. Best for pre-defined kits."
            ShoppingVerdict.SKIP -> "Versatility: Low pairing potential. Limits styling flexibility."
        },
        outfitCountEstimate = when(verdict) {
            ShoppingVerdict.GOOD_MATCH -> "Creates 4-6 outfits compatibility"
            ShoppingVerdict.MAYBE -> "Creates 2-3 outfits compatibility"
            ShoppingVerdict.SKIP -> "1 outfit maximum"
        },
        shoppingAdvice = when(verdict) {
            ShoppingVerdict.GOOD_MATCH -> "Highly recommended purchase. Functional wardrobe expansion item."
            ShoppingVerdict.MAYBE -> "Double check if you can coordinate this with 3 independent existing items."
            ShoppingVerdict.SKIP -> "Highly advisable to pass. This item is statistically prone to remain unworn."
        }
    )
}
```

---

## 📁 Storage Contracts

### 💾 Jetpack DataStore (User Preferences)
```kotlin
object PreferencesKeys {
    val APP_LANGUAGE = stringPreferencesKey("app_language") // "EN" or "RU"
    val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    val PRIVACY_CONSENT_ACCEPTED = booleanPreferencesKey("privacy_consent_accepted")
    val ALLUDED_DAILY_CREDITS = intPreferencesKey("alluded_daily_credits") // Default: 3
    val REPORT_GENERATED = booleanPreferencesKey("report_generated")
}
```

### 🗄️ Room Database (Wardrobe & Decisions cache)

```kotlin
@Entity(tableName = "wardrobe_items")
data class WardrobeItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val color: String,
    val colorDir: String,
    val season: String,
    val versatilityScore: Int,
    val outfitsCount: Int,
    val rarelyWorn: Boolean,
    val isWishlist: Boolean,
    val hexColor: String
)

@Entity(tableName = "saved_decisions")
data class SavedDecisionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String,
    val context: String,
    val colorDir: String,
    val verdict: String,
    val reasonEn: String,
    val reasonRu: String,
    val outfitsCount: Int,
    val dateString: String
)
```
