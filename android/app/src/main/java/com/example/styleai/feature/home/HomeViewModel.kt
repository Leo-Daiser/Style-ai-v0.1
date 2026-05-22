package com.example.styleai.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.CreditBalance
import com.example.styleai.domain.model.StyleReport
import com.example.styleai.domain.repository.BillingRepository
import com.example.styleai.domain.repository.StyleRepository
import kotlinx.coroutines.flow.*

class HomeViewModel(
    private val styleRepository: StyleRepository,
    private val billingRepository: BillingRepository
) : ViewModel() {

    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    val activeReport: StateFlow<StyleReport?> = styleRepository.getActiveReport()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val creditBalance: StateFlow<CreditBalance> = billingRepository.getCreditBalance()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreditBalance(0, false))

    val savedLooksCount: StateFlow<Int> = styleRepository.getSavedLooks()
        .map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val historyCount: StateFlow<Int> = styleRepository.getHistory()
        .map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val hasCompletedStyleReport: StateFlow<Boolean> = activeReport
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}
