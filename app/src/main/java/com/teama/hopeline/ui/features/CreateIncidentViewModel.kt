package com.teama.hopeline.ui.features

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teama.hopeline.data.ApiService
import com.teama.hopeline.data.model.Incident
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class CreateIncidentViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val _uiState = MutableStateFlow(CreateIncidentUiState())
    val uiState: StateFlow<CreateIncidentUiState> = _uiState.asStateFlow()

    fun saveIncident(incident: Incident) {
        viewModelScope.launch {
            runCatching {
                apiService.saveIncidents(incident)
            }.onSuccess {

            }.onFailure {
                Log.d("err", it.message.toString())
            }
        }
    }
}

data class CreateIncidentUiState(
    val error: String = ""
)