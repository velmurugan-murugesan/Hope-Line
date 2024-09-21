package com.teama.hopeline.ui.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.teama.hopeline.data.AppPreference
import com.teama.hopeline.data.AppConstants

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val username = remember { AppPreference.getStringValue(AppConstants.KEY_USERNAME) }
    var isVolunteer by remember { mutableStateOf(AppPreference.getStringValue(AppConstants.KEY_IS_VOLUNTEER).toBoolean()) }

    val helplineNumbers = listOf(
        "Government Service 1: 123-456-7890",
        "Government Service 2: 098-765-4321",
        "Help Center: 111-222-3333"
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Profile", style = MaterialTheme.typography.headlineMedium)
                Text("Username: $username", style = MaterialTheme.typography.titleMedium)
                Text("Volunteer: ${if (isVolunteer) "Yes" else "No"}", style = MaterialTheme.typography.titleMedium)
                if (!isVolunteer) {
                    Button(onClick = {
                        isVolunteer = true
                        AppPreference.saveString(AppConstants.KEY_IS_VOLUNTEER, "true")
                    }) {
                        Text("Become a Volunteer")
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Donations", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { /* Handle donation click */ }) {
                Text("Donate Now")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Helpline", style = MaterialTheme.typography.headlineSmall)
        }

        items(helplineNumbers) { number ->
            Text(number, style = MaterialTheme.typography.bodyLarge)
        }
    }
}