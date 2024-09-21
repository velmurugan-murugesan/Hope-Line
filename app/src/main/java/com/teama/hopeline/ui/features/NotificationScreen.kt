package com.teama.hopeline.ui.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NotificationScreen(
     viewModel: NotificationViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize()) {
        LazyColumn {
            items(uiState.value.incidents) {
                IncidentCard(it)
            }
        }
    }
}