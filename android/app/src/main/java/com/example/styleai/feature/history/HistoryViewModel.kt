package com.example.styleai.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.model.HistoryItem
import com.example.styleai.domain.model.SavedLook
import com.example.styleai.domain.repository.StyleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val styleRepository: StyleRepository
) : ViewModel() {

    val historyItems: StateFlow<List<HistoryItem>> = styleRepository.getHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedLooks: StateFlow<List<SavedLook>> = styleRepository.getSavedLooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteHistoryItem(id: String) {
        viewModelScope.launch {
            styleRepository.deleteHistoryItem(id)
        }
    }

    fun removeSavedLook(id: String) {
        viewModelScope.launch {
            styleRepository.deleteSavedLook(id)
        }
    }
}
