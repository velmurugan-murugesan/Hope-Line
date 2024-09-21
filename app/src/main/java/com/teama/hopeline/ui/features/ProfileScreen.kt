package com.teama.hopeline.ui.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.teama.hopeline.data.AppPreference
import com.teama.hopeline.data.AppConstants

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val username = remember { AppPreference.getStringValue(AppConstants.KEY_USERNAME) }
    val isVolunteer = remember { AppPreference.getStringValue(AppConstants.KEY_IS_VOLUNTEER).toBoolean() }

    Column(Modifier.fillMaxSize()) {
        Text("Profile")
        Text("Username: $username")
        Text("Volunteer: ${if (isVolunteer) "Yes" else "No"}")
    }
}