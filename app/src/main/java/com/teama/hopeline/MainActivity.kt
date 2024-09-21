package com.teama.hopeline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teama.hopeline.ui.features.CreateIncidentScreen
import com.teama.hopeline.ui.features.HomeScreen
import com.teama.hopeline.ui.features.LoginScreen
import com.teama.hopeline.ui.features.NotificationScreen
import com.teama.hopeline.ui.features.ProfileScreen
import com.teama.hopeline.ui.features.SplashScreen
import com.teama.hopeline.ui.theme.HopeLineTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HopeLineTheme {

                val isShowBottomBar by remember {
                    mutableStateOf(false)
                }
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),

                    bottomBar = {
                            BottomNavigationBar(navController)
                    },
                    floatingActionButton = {
                        Button(onClick = {
                            navController.navigate(BottomNavItem.CreateIncident.route)
                        }) {
                            Text("+ Add ")
                        }
                    }
                    ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavItem.Splash.route) {
                            SplashScreen(navController)
                        }

                        composable(BottomNavItem.Login.route) {
                            LoginScreen(navController)
                        }

                        composable(BottomNavItem.Home.route) {
                            HomeScreen()
                        }


                        composable(BottomNavItem.Notification.route) {
                            NotificationScreen()
                        }

                        composable(BottomNavItem.Profile.route) {
                            ProfileScreen(
                                navController = navController
                            )
                        }

                        composable(BottomNavItem.CreateIncident.route) {
                            CreateIncidentScreen()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = Color.Blue,
        contentColor = Color.Red
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Notification,
            BottomNavItem.Profile
        )

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                },
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = screen.icon,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                label = { Text(text = screen.label, color = Color.White) },
                colors = colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.LightGray
                ),
            )
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Splash: BottomNavItem("splash", Icons.Default.Home, "Splash")
    data object Login : BottomNavItem("login", Icons.Default.Home, "Login")
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object Notification : BottomNavItem("notification", Icons.Default.Search, "Notification")
    data object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
    data object CreateIncident : BottomNavItem("createIncident", Icons.Default.Person, "Create Incident")
}
