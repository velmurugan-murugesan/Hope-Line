package com.teama.hopeline.ui.features

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RescueAidScreen() {
    var answer by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var additionalInfo by remember { mutableStateOf("") }
    var showDetails by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Do you have skills to aid with rescue stuff?", style = MaterialTheme.typography.headlineMedium)

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            RadioButton(
                selected = answer == "Yes",
                onClick = {
                    answer = "Yes"
                    showDetails = true
                }
            )
            Text("Yes", modifier = Modifier.padding(start = 8.dp))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = answer == "No",
                onClick = {
                    answer = "No"
                    showDetails = false
                }
            )
            Text("No", modifier = Modifier.padding(start = 8.dp))
        }

        if (showDetails) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Please list your skills:", style = MaterialTheme.typography.titleMedium)
            BasicTextField(
                value = skills,
                onValueChange = { skills = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Location of the area:", style = MaterialTheme.typography.titleMedium)
            BasicTextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Additional Information:", style = MaterialTheme.typography.titleMedium)
            BasicTextField(
                value = additionalInfo,
                onValueChange = { additionalInfo = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )
        }
    }
}