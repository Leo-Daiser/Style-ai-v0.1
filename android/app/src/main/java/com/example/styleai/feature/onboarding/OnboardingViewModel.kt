package com.example.styleai.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.UserConsentState
import com.example.styleai.domain.repository.StyleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val styleRepository: StyleRepository
) : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _consentState = MutableStateFlow(UserConsentState())
    val consentState: StateFlow<UserConsentState> = _consentState.asStateFlow()

    init {
        viewModelScope.launch {
            styleRepository.getConsentState().collect { saved ->
                _consentState.value = saved
            }
        }
    }

    fun setPage(page: Int) {
        _currentPage.value = page
    }

    fun updateConsent(
        photoPermission: Boolean? = null,
        understandsDisclaimer: Boolean? = null,
        rawPhotosNotStored: Boolean? = null
    ) {
        _consentState.update { current ->
            current.copy(
                hasPhotoPermission = photoPermission ?: current.hasPhotoPermission,
                understandsDisclaimer = understandsDisclaimer ?: current.understandsDisclaimer,
                rawPhotosNotStored = rawPhotosNotStored ?: current.rawPhotosNotStored
            )
        }
    }

    fun submitConsent(onSuccess: () -> Unit) {
        if (_consentState.value.isFullyConsented) {
            viewModelScope.launch {
                styleRepository.saveConsentState(_consentState.value)
                onSuccess()
            }
        }
    }
}
