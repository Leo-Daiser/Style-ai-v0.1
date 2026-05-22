package com.example.styleai.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.repository.StyleRepository
import com.example.styleai.core.privacy.SafeLogger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val styleRepository: StyleRepository
) : ViewModel() {

    private val _isExporting = MutableStateFlow(false)
    val isExporting: StateFlow<Boolean> = _isExporting.asStateFlow()

    private val _dataDeletedMessage = MutableStateFlow<String?>(null)
    val dataDeletedMessage: StateFlow<String?> = _dataDeletedMessage.asStateFlow()

    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    private val _versionTapCount = MutableStateFlow(0)
    val versionTapCount: StateFlow<Int> = _versionTapCount.asStateFlow()

    val isDeveloperMode: StateFlow<Boolean> = _versionTapCount
        .map { it >= 5 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun changeLanguage(lang: AppLanguage) {
        viewModelScope.launch {
            styleRepository.saveSelectedLanguage(lang)
            SafeLogger.i("Language manually switched to: ${lang.name}")
        }
    }

    fun tapVersion() {
        _versionTapCount.value += 1
        SafeLogger.i("App version tapped. Current count: ${_versionTapCount.value}")
    }

    /**
     * Delete style data only: clears reports / visual outfits, preserves language & onboarding.
     */
    fun deleteStyleDataOnly(onCompleted: () -> Unit) {
        viewModelScope.launch {
            styleRepository.clearAllLocalData()
            _dataDeletedMessage.value = "All localized style profiles, seasonally generated report palettes, and matching outfits have been safely deleted."
            onCompleted()
        }
    }

    /**
     * Reset app onboarding: wipes consent states and completed markers.
     */
    fun resetAppOnboarding(onCompleted: () -> Unit) {
        viewModelScope.launch {
            styleRepository.saveOnboardingCompleted(false)
            styleRepository.saveConsentState(com.example.styleai.domain.model.UserConsentState())
            styleRepository.clearPhotos()
            _dataDeletedMessage.value = "App onboarding and safety consents have been completely reset to default setup."
            onCompleted()
        }
    }

    fun clearSavedLooksOnly() {
        viewModelScope.launch {
            styleRepository.clearPhotos()
            _dataDeletedMessage.value = "Cached transient photo descriptors and look grids cleared."
        }
    }

    fun exportReport(onReportReady: (String) -> Unit) {
        viewModelScope.launch {
            _isExporting.value = true
            kotlinx.coroutines.delay(1200) // Simulated build latency
            _isExporting.value = false
            onReportReady("StyleAI-ActiveStyleReport-Autumn.json")
        }
    }

    fun dismissMessage() {
        _dataDeletedMessage.value = null
    }

    fun resetVersionTaps() {
        _versionTapCount.value = 0
    }
}
