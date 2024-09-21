package com.teama.hopeline.ui.features

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teama.hopeline.data.model.Incident

@Composable
fun IncidentListScreen(incidents: List<Incident>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(incidents) { incident ->
            IncidentCard(incident = incident)
        }
    }
}