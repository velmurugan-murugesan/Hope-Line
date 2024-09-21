package com.teama.hopeline.ui.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.teama.hopeline.BottomNavItem
import com.teama.hopeline.data.AppPreference
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    Column(Modifier.fillMaxSize()) {
        Text("Splash Screen")


        LaunchedEffect(Unit) {
            delay(2000)
            val token = AppPreference.getStringValue("token")
            if (token.isEmpty()) {
                navController.navigate(BottomNavItem.Login.route)
            } else {
                navController.navigate(BottomNavItem.Home.route)
            }
        }
    }
}