package com.example.styleai.feature.visualization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.*
import com.example.styleai.domain.repository.BillingRepository
import com.example.styleai.domain.repository.StyleRepository
import com.example.styleai.core.privacy.SafeLogger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface VisualizerUiState {
    object Idle : VisualizerUiState
    object Generating : VisualizerUiState
    data class GenerationSuccess(val cleanOutfitId: String) : VisualizerUiState
    data class InsufficientCredits(val req: Int) : VisualizerUiState
    data class BannedWordDetected(val word: String) : VisualizerUiState
}

class VisualizationViewModel(
    private val styleRepository: StyleRepository,
    private val billingRepository: BillingRepository
) : ViewModel() {

    private val _selectedOccasion = MutableStateFlow(Occasion.EVERYDAY)
    val selectedOccasion: StateFlow<Occasion> = _selectedOccasion.asStateFlow()

    private val _selectedStyle = MutableStateFlow(StyleType.MINIMAL)
    val selectedStyle: StateFlow<StyleType> = _selectedStyle.asStateFlow()

    private val _selectedSeason = MutableStateFlow(Season.AUTUMN)
    val selectedSeason: StateFlow<Season> = _selectedSeason.asStateFlow()

    private val _selectedFormality = MutableStateFlow(Formality.POLISHED)
    val selectedFormality: StateFlow<Formality> = _selectedFormality.asStateFlow()

    private val _selectedColorDirection = MutableStateFlow(ColorDirection.NEUTRAL)
    val selectedColorDirection: StateFlow<ColorDirection> = _selectedColorDirection.asStateFlow()

    private val _uiState = MutableStateFlow<VisualizerUiState>(VisualizerUiState.Idle)
    val uiState: StateFlow<VisualizerUiState> = _uiState.asStateFlow()

    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    val CreditBalance: StateFlow<CreditBalance> = billingRepository.getCreditBalance()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreditBalance(3, false))

    val outfitIdeas: StateFlow<List<OutfitIdea>> = combine(
        _selectedOccasion, _selectedStyle, _selectedSeason
    ) { occ, sty, sea ->
        Triple(occ, sty, sea)
    }.flatMapLatest { (occ, sty, sea) ->
        styleRepository.getOutfitIdeas(occ.name, sty.name, sea.name)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedLooks: StateFlow<List<SavedLook>> = styleRepository.getSavedLooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selector update routines
    fun changeOccasion(occ: Occasion) { _selectedOccasion.value = occ }
    fun changeStyle(sty: StyleType) { _selectedStyle.value = sty }
    fun changeSeason(sea: Season) { _selectedSeason.value = sea }
    fun changeFormality(form: Formality) { _selectedFormality.value = form }
    fun changeColorDirection(dir: ColorDirection) { _selectedColorDirection.value = dir }

    /**
     * Synthesizes an OutfitIdea strictly from the selected, safe enum choices.
     * This avoids any arbitrary text input entirely!
     */
    fun triggerControlledVisualization() {
        viewModelScope.launch {
            _uiState.value = VisualizerUiState.Generating
            val currentBalance = CreditBalance.value
            
            // Subtraction check
            if (!currentBalance.isProUser && currentBalance.totalCredits < 1) {
                _uiState.value = VisualizerUiState.InsufficientCredits(1)
                return@launch
            }

            try {
                // Initialize request object. This runs the model-level banned keyword checks if any triggers occur,
                // but since these parameters are type-safe enums, safety is guaranteed.
                val request = VisualizationRequest(
                    reportId = "active_mvp_report",
                    occasion = _selectedOccasion.value,
                    style = _selectedStyle.value,
                    season = _selectedSeason.value,
                    formality = _selectedFormality.value,
                    colorDirection = _selectedColorDirection.value
                )

                val debited = billingRepository.useCredit(1)
                if (debited) {
                    SafeLogger.i("Visualization credit processed securely for query: $request")
                    kotlinx.coroutines.delay(1200) // simulated model rendering delay
                    _uiState.value = VisualizerUiState.GenerationSuccess("outfit_gen_${System.currentTimeMillis()}")
                } else {
                    _uiState.value = VisualizerUiState.InsufficientCredits(1)
                }
            } catch (e: SecurityException) {
                // Caught security trigger
                _uiState.value = VisualizerUiState.BannedWordDetected(e.message ?: "banned theme")
            }
        }
    }

    fun toggleSaveOutfit(outfitId: String) {
        viewModelScope.launch {
            styleRepository.toggleSaveLook(outfitId)
        }
    }

    fun removeSavedLook(id: String) {
        viewModelScope.launch {
            styleRepository.deleteSavedLook(id)
        }
    }

    fun resetState() {
        _uiState.value = VisualizerUiState.Idle
    }
}
