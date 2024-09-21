package com.teama.hopeline.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teama.hopeline.data.ApiService
import com.teama.hopeline.data.model.Incident
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getAllIncidents()
    }
    fun getAllIncidents() {
        viewModelScope.launch {
            runCatching {
                apiService.getAllIncidents()
            }.onSuccess { incidents ->
                _uiState.value = _uiState.value.copy(incidents = incidents)
            }
        }
    }
}

data class NotificationUiState(
    val incidents: List<Incident> = emptyList()

)