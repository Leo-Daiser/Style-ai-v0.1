package com.example.styleai.feature.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.*
import com.example.styleai.domain.repository.StyleRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface UploadUiState {
    object Idle : UploadUiState
    data class Validating(val message: String) : UploadUiState
    data class ValidationFailed(val reason: String, val warnings: List<String>) : UploadUiState
    data class Analyzing(val stepIndex: Int, val currentStep: String, val percentage: Int) : UploadUiState
    data class AnalysisSuccess(val report: StyleReport) : UploadUiState
    data class Error(val errorMessage: String) : UploadUiState
}

class UploadViewModel(
    private val styleRepository: StyleRepository
) : ViewModel() {

    private val _selfiePhoto = MutableStateFlow<UploadedPhoto?>(null)
    val selfiePhoto: StateFlow<UploadedPhoto?> = _selfiePhoto.asStateFlow()

    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    private val _fullBodyPhoto = MutableStateFlow<UploadedPhoto?>(null)
    val fullBodyPhoto: StateFlow<UploadedPhoto?> = _fullBodyPhoto.asStateFlow()

    private val _uiState = MutableStateFlow<UploadUiState>(UploadUiState.Idle)
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    private val analysisSteps = listOf(
        "Checking photo safety & moderation tags...",
        "Analyzing tone pigments & soft season color palette...",
        "Evaluating shoulder geometries & best silhouettes...",
        "Matching matching style directions (Minimal Classic, Smart/Casual)...",
        "Formulating tailored wardrobe gap list...",
        "Compiling style shopping indices and report..."
    )

    fun selectSelfie(uri: String) {
        val photo = UploadedPhoto(uri, PhotoType.SELFIE_FACE)
        _selfiePhoto.value = photo
        validateUploadedPhoto(photo)
    }

    fun selectFullBody(uri: String) {
        val photo = UploadedPhoto(uri, PhotoType.FULL_BODY)
        _fullBodyPhoto.value = photo
        validateUploadedPhoto(photo)
    }

    private fun validateUploadedPhoto(photo: UploadedPhoto) {
        viewModelScope.launch {
            _uiState.value = UploadUiState.Validating("Scanning image metadata...")
            val result = styleRepository.validatePhoto(photo)
            if (!result.isValid) {
                _uiState.value = UploadUiState.ValidationFailed(
                    reason = result.reason ?: "Unknown validation mismatch",
                    warnings = result.warnings
                )
            } else {
                _uiState.value = UploadUiState.Idle
            }
        }
    }

    fun startAnalysis() {
        val selfie = _selfiePhoto.value
        val fullBody = _fullBodyPhoto.value

        if (selfie == null || fullBody == null) {
            _uiState.value = UploadUiState.Error("Please upload both face and full-body photos to continue.")
            return
        }

        viewModelScope.launch {
            // Sequential mock loading simulator matching the requested progress step UI
            for (i in analysisSteps.indices) {
                val progressPercent = ((i + 1) * 100) / analysisSteps.size
                _uiState.value = UploadUiState.Analyzing(
                    stepIndex = i,
                    currentStep = analysisSteps[i],
                    percentage = progressPercent
                )
                delay(1200) // Small cinematic buffer
            }

            try {
                val report = styleRepository.generateReport(selfie, fullBody)
                _uiState.value = UploadUiState.AnalysisSuccess(report)
            } catch (e: Exception) {
                _uiState.value = UploadUiState.Error("Style analysis failed. Please try again.")
            }
        }
    }

    fun clearUploads() {
        _selfiePhoto.value = null
        _fullBodyPhoto.value = null
        _uiState.value = UploadUiState.Idle
        viewModelScope.launch {
            styleRepository.clearPhotos()
        }
    }

    fun resetState() {
        _uiState.value = UploadUiState.Idle
    }
}
