package com.example.styleai.domain.model

import java.util.Date

/**
 * Supported app languages for inline translation.
 */
enum class AppLanguage {
    EN, RU
}

/**
 * Controlled categorical parameters for style rendering.
 */
enum class Occasion {
    EVERYDAY, OFFICE, DATE, TRAVEL, EVENT
}

enum class StyleType {
    MINIMAL, CLASSIC, SMART_CASUAL, FEMININE, STREETWEAR
}

enum class Season {
    SPRING, SUMMER, AUTUMN, WINTER
}

enum class Formality {
    CASUAL, POLISHED, FORMAL
}

enum class ColorDirection {
    NEUTRAL, WARM, COOL, SOFT, CONTRAST
}

/**
 * Clean Request model that is constructed ONLY from safe enum choices.
 * This completely prevents free-text prompt injections or illegal keywords at the type level.
 */
data class VisualizationRequest(
    val reportId: String,
    val occasion: Occasion,
    val style: StyleType,
    val season: Season,
    val formality: Formality,
    val colorDirection: ColorDirection
) {
    init {
        // Validation check against explicit banned terms
        val combined = "${occasion.name} ${style.name} ${season.name} ${formality.name} ${colorDirection.name}".lowercase()
        val bannedKeywords = listOf(
            "underwear", "lingerie", "bikini", "swimsuit", "transparent", 
            "erotic", "fetish", "nude", "naked", "schoolgirl", "school_girl",
            "age_regression", "childlike", "sexualized", "remove_clothes", "younger", "sexier"
        )
        for (keyword in bannedKeywords) {
            if (combined.contains(keyword)) {
                throw SecurityException("Safety breach: Forbidden theme '$keyword' matched in query parameters!")
            }
        }
    }
}

/**
 * Represents the user's explicit safety and privacy consent choices.
 */
data class UserConsentState(
    val hasPhotoPermission: Boolean = false,
    val understandsDisclaimer: Boolean = false,
    val rawPhotosNotStored: Boolean = false
) {
    val isFullyConsented: Boolean
        get() = hasPhotoPermission && understandsDisclaimer && rawPhotosNotStored
}

/**
 * Representation of an uploaded image containing local details.
 */
data class UploadedPhoto(
    val uriString: String,
    val photoType: PhotoType,
    val timestamp: Long = System.currentTimeMillis()
)

enum class PhotoType {
    SELFIE_FACE,
    FULL_BODY
}

/**
 * Result of the photo safety and validation checks.
 */
data class PhotoValidationResult(
    val isValid: Boolean,
    val reason: String? = null,
    val warnings: List<String> = emptyList()
)

/**
 * Detailed personal color palette properties.
 */
data class ColorPalette(
    val name: String,
    val description: String,
    val swatchColors: List<String>, // Hex list e.g. ["#E6D5C3", "#8D5B4C"]
    val priorityColors: List<String>,
    val avoidColors: List<String>
)

/**
 * Structured details about suggested silhouettes and fits.
 */
data class SilhouetteRecommendation(
    val title: String,
    val description: String,
    val category: String // e.g., Tops, Bottoms, Outerwear
)

/**
 * Personal style direction card properties.
 */
data class StyleDirection(
    val title: String,
    val description: String,
    val bestUseCases: String,
    val recommendedColors: List<String>,
    val recommendedItems: List<String>
)

/**
 * A wardrobe gap recommendation checklist item.
 */
data class WardrobeGap(
    val id: String,
    val itemName: String,
    val isAcquired: Boolean = false,
    val description: String,
    val importance: String // e.g., Essential, Recommended, Optional
)

/**
 * Prioritized item in the shopping guide.
 */
data class ShoppingItem(
    val id: String,
    val priority: Int,
    val title: String,
    val expectedPriceRange: String,
    val stylingNotes: String
)

/**
 * Overall style report containing the consolidated analysis.
 */
data class StyleReport(
    val id: String,
    val date: Long = System.currentTimeMillis(),
    val colorPalette: ColorPalette,
    val silhouettes: List<SilhouetteRecommendation>,
    val styleDirections: List<StyleDirection>,
    val wardrobeGaps: List<WardrobeGap>,
    val shoppingList: List<ShoppingItem>,
    val whatToAvoid: List<String>
)

/**
 * Safe outfit visualization parameters and outcome.
 */
data class OutfitIdea(
    val id: String,
    val title: String,
    val occasion: String,
    val style: String,
    val season: String,
    val clothingItems: List<String>,
    val colorPalette: List<String>,
    val imagePlaceholderResId: String, // String representation or name
    val isSaved: Boolean = false
)

/**
 * State representing user's current credit counts.
 */
data class CreditBalance(
    val totalCredits: Int,
    val isProUser: Boolean = false
)

/**
 * Purchase plans for in-app mock purchases.
 */
data class PurchasePlan(
    val id: String,
    val name: String,
    val description: String,
    val priceText: String,
    val features: List<String>,
    val isSubscription: Boolean = false
)

/**
 * A look that was explicitly saved by the user.
 */
data class SavedLook(
    val id: String,
    val title: String,
    val outfitId: String,
    val savedAt: Long = System.currentTimeMillis(),
    val imagePlaceholderHex: String = "#F3EFE9"
)

/**
 * A history item tracking previously generated style records.
 */
data class HistoryItem(
    val id: String,
    val date: Long,
    val reportTitle: String,
    val paletteName: String,
    val numSavedLooks: Int
)
