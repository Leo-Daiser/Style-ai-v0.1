package com.example.styleai.feature.paywall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.CreditBalance
import com.example.styleai.domain.model.PurchasePlan
import com.example.styleai.domain.repository.BillingRepository
import com.example.styleai.domain.repository.StyleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class PaywallUiState {
    object Idle : PaywallUiState()
    object Processing : PaywallUiState()
    data class Success(val message: String) : PaywallUiState()
    data class Error(val error: String) : PaywallUiState()
}

class PaywallViewModel(
    private val billingRepository: BillingRepository,
    private val styleRepository: StyleRepository
) : ViewModel() {

    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    val creditBalance: StateFlow<CreditBalance> = billingRepository.getCreditBalance()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreditBalance(3, false))

    private val _uiState = MutableStateFlow<PaywallUiState>(PaywallUiState.Idle)
    val uiState: StateFlow<PaywallUiState> = _uiState.asStateFlow()

    fun getPurchasePlans(): List<PurchasePlan> {
        return billingRepository.getPurchasePlans()
    }

    fun purchasePlan(planId: String) {
        viewModelScope.launch {
            _uiState.value = PaywallUiState.Processing
            // Fake brief network delay
            kotlinx.coroutines.delay(1000)
            val success = billingRepository.purchasePlan(planId)
            if (success) {
                _uiState.value = PaywallUiState.Success(
                    if (selectedLanguage.value == AppLanguage.EN) "Purchase successful! Account updated." else "Покупка успешно завершена!"
                )
            } else {
                _uiState.value = PaywallUiState.Error(
                    if (selectedLanguage.value == AppLanguage.EN) "Purchase failed. Try again." else "Ошибка при оплате. Попробуйте еще раз."
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = PaywallUiState.Idle
    }
}
