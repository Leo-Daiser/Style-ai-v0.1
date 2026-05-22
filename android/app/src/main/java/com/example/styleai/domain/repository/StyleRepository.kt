package com.example.styleai.domain.repository

import com.example.styleai.domain.model.*
import kotlinx.coroutines.flow.Flow

interface StyleRepository {
    fun getConsentState(): Flow<UserConsentState>
    suspend fun saveConsentState(state: UserConsentState)

    // Language Preference Management
    fun getSelectedLanguage(): Flow<AppLanguage>
    suspend fun saveSelectedLanguage(language: AppLanguage)

    // Onboarding State
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun saveOnboardingCompleted(completed: Boolean)

    fun getUploadedPhotos(): Flow<List<UploadedPhoto>>
    suspend fun addUploadedPhoto(photo: UploadedPhoto)
    suspend fun validatePhoto(photo: UploadedPhoto): PhotoValidationResult
    suspend fun clearPhotos()

    fun getActiveReport(): Flow<StyleReport?>
    suspend fun generateReport(selfie: UploadedPhoto, fullBody: UploadedPhoto): StyleReport
    fun getHistory(): Flow<List<HistoryItem>>
    suspend fun deleteHistoryItem(id: String)
    suspend fun clearAllLocalData()

    fun getOutfitIdeas(occasion: String?, style: String?, season: String?): Flow<List<OutfitIdea>>
    fun getSavedLooks(): Flow<List<SavedLook>>
    suspend fun toggleSaveLook(outfitId: String)
    suspend fun deleteSavedLook(id: String)
}
