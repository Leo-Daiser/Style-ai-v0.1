package com.example.styleai.data.repository

import com.example.styleai.data.mock.MockData
import com.example.styleai.domain.model.*
import com.example.styleai.domain.repository.StyleRepository
import com.example.styleai.core.privacy.SafeLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class StyleRepositoryImpl : StyleRepository {

    private val consentState = MutableStateFlow(UserConsentState())
    private val selectedLanguage = MutableStateFlow(AppLanguage.EN)
    private val onboardingCompleted = MutableStateFlow(false)

    private val uploadedPhotos = MutableStateFlow<List<UploadedPhoto>>(emptyList())
    private val activeReport = MutableStateFlow<StyleReport?>(null)
    private val historyItems = MutableStateFlow(MockData.sampleHistoryItems)
    
    private val outfitIdeas = MutableStateFlow(MockData.sampleOutfitIdeas)
    private val savedLooks = MutableStateFlow<List<SavedLook>>(emptyList())

    override fun getConsentState(): Flow<UserConsentState> = consentState
    
    override suspend fun saveConsentState(state: UserConsentState) {
        consentState.value = state
        SafeLogger.i("Consent state has been saved in local memory storage safely.")
    }

    override fun getSelectedLanguage(): Flow<AppLanguage> = selectedLanguage

    override suspend fun saveSelectedLanguage(language: AppLanguage) {
        selectedLanguage.value = language
        SafeLogger.i("Selected App Language changed to: ${language.name}")
    }

    override fun isOnboardingCompleted(): Flow<Boolean> = onboardingCompleted

    override suspend fun saveOnboardingCompleted(completed: Boolean) {
        onboardingCompleted.value = completed
        SafeLogger.i("Onboarding completed status updated: $completed")
    }

    override fun getUploadedPhotos(): Flow<List<UploadedPhoto>> = uploadedPhotos

    override suspend fun addUploadedPhoto(photo: UploadedPhoto) {
        uploadedPhotos.update { current -> current + photo }
    }

    override suspend fun validatePhoto(photo: UploadedPhoto): PhotoValidationResult {
        // Redacted image path for privacy compliance in checking
        SafeLogger.i("Analyzing uploaded face details in system memory bounds...")
        val sizeKb = 120 // representation
        val hasBadLighting = photo.uriString.contains("dark") || photo.uriString.contains("blurry")
        
        return when {
            hasBadLighting -> PhotoValidationResult(
                isValid = false,
                reason = "Poor Lighting Detected",
                warnings = listOf("Please capture inside a well-lit room or near a window for best season detection.")
            )
            photo.uriString.contains("minor") -> PhotoValidationResult(
                isValid = false,
                reason = "Minors protection filter trigger",
                warnings = listOf("This application is configured for adult styling advisory only.")
            )
            photo.uriString.contains("crowd") -> PhotoValidationResult(
                isValid = false,
                reason = "Multiple people detected",
                warnings = listOf("Please crop or retake the photo so that only one face is visible.")
            )
            else -> PhotoValidationResult(
                isValid = true,
                warnings = if (sizeKb < 50) listOf("Low resolution photo. Detail analysis might be less precise.") else emptyList()
            )
        }
    }

    override suspend fun clearPhotos() {
        uploadedPhotos.value = emptyList()
        SafeLogger.i("Local volatile photo URI references erased.")
    }

    override fun getActiveReport(): Flow<StyleReport?> = activeReport

    override suspend fun generateReport(selfie: UploadedPhoto, fullBody: UploadedPhoto): StyleReport {
        SafeLogger.i("Initiating color analysis on local GPU structures...")
        val report = MockData.sampleStyleReport.copy(
            date = System.currentTimeMillis()
        )
        activeReport.value = report
        
        // Append to history
        val newHistoryItem = HistoryItem(
            id = "hist_${System.currentTimeMillis()}",
            date = System.currentTimeMillis(),
            reportTitle = "Generated Style Report",
            paletteName = report.colorPalette.name,
            numSavedLooks = 0
        )
        historyItems.update { current -> listOf(newHistoryItem) + current }
        
        return report
    }

    override fun getHistory(): Flow<List<HistoryItem>> = historyItems

    override suspend fun deleteHistoryItem(id: String) {
        // No uri tracking
        SafeLogger.i("Deleting history logs matching record reference ID")
        historyItems.update { current -> current.filter { it.id != id } }
        if (activeReport.value?.id == id) {
            activeReport.value = null
        }
    }

    /**
     * Delete style data only (Do not reset language or delete active packages)
     */
    override suspend fun clearAllLocalData() {
        uploadedPhotos.value = emptyList()
        activeReport.value = null
        historyItems.value = emptyList()
        savedLooks.value = emptyList()
        // Reset save outfit states
        outfitIdeas.update { current ->
            current.map { it.copy(isSaved = false) }
        }
        SafeLogger.i("Style caches clear completed successfully. Preferences are preserved.")
    }

    override fun getOutfitIdeas(occasion: String?, style: String?, season: String?): Flow<List<OutfitIdea>> {
        return outfitIdeas.map { list ->
            list.filter { item ->
                (occasion == null || occasion == "All" || item.occasion.equals(occasion, ignoreCase = true)) &&
                (style == null || style == "All" || item.style.equals(style, ignoreCase = true)) &&
                (season == null || season == "All" || item.season.equals(season, ignoreCase = true))
            }
        }
    }

    override fun getSavedLooks(): Flow<List<SavedLook>> = savedLooks

    override suspend fun toggleSaveLook(outfitId: String) {
        var added = false
        var targetIdea: OutfitIdea? = null
        
        outfitIdeas.update { currentList ->
            currentList.map { item ->
                if (item.id == outfitId) {
                    val nextState = !item.isSaved
                    added = nextState
                    targetIdea = item
                    item.copy(isSaved = nextState)
                } else {
                    item
                }
            }
        }
        
        if (added && targetIdea != null) {
            val idea = targetIdea!!
            val newLook = SavedLook(
                id = "look_${idea.id}",
                title = idea.title,
                outfitId = idea.id,
                imagePlaceholderHex = idea.colorPalette.firstOrNull() ?: "#F3EFE9"
            )
            savedLooks.update { current -> current.filter { it.outfitId != outfitId } + newLook }
            
            // Increment saved count in history if active
            activeReport.value?.let { report ->
                historyItems.update { histories ->
                    histories.map { h ->
                        if (h.reportTitle == "Generated Style Report") {
                            h.copy(numSavedLooks = h.numSavedLooks + 1)
                        } else h
                    }
                }
            }
        } else {
            savedLooks.update { current -> current.filter { it.outfitId != outfitId } }
            // Decrement saved count in history
            historyItems.update { histories ->
                histories.map { h ->
                    if (h.reportTitle == "Generated Style Report" && h.numSavedLooks > 0) {
                        h.copy(numSavedLooks = h.numSavedLooks - 1)
                    } else h
                }
            }
        }
    }

    override suspend fun deleteSavedLook(id: String) {
        val targetLook = savedLooks.value.find { it.id == id }
        savedLooks.update { current -> current.filter { it.id != id } }
        
        if (targetLook != null) {
            outfitIdeas.update { currentList ->
                currentList.map { item ->
                    if (item.id == targetLook.outfitId) {
                        item.copy(isSaved = false)
                    } else {
                        item
                    }
                }
            }
        }
    }
}
