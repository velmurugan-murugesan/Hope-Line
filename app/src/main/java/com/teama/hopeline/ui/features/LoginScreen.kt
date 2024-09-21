package com.teama.hopeline.ui.features

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.teama.hopeline.BottomNavItem
import com.teama.hopeline.data.AppConstants
import com.teama.hopeline.data.AppPreference
import com.teama.hopeline.ui.theme.HopeLineTheme

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        Text("Login Screen")
    }
    val googleSignInClient = remember { getGoogleSignInClient(context) }
    val signInLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task, onSuccess = {
                navController.navigate(BottomNavItem.Home.route) {
                    popUpTo(BottomNavItem.Login.route) {
                        inclusive = true
                    }

                }
            })
        }


    LaunchedEffect(Unit) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account == null) {
            signInLauncher.launch(googleSignInClient.signInIntent)
        }
    }
}

private fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

private fun handleSignInResult(
    completedTask: Task<GoogleSignInAccount>,
    onSuccess: () -> Unit
) {
    try {
        val account = completedTask.getResult(ApiException::class.java)
        // Signed in successfully, show authenticated UI.
        // You can now use the account object to access user information
        AppPreference.saveString("token", account.idToken.orEmpty())
        AppPreference.saveString(AppConstants.KEY_USERNAME, account.account?.name.orEmpty())

        // Assuming you have a way to determine if the user is a volunteer
        val isVolunteer = determineIfVolunteer(account)
        AppPreference.saveString(AppConstants.KEY_IS_VOLUNTEER, isVolunteer.toString())
        onSuccess()

    } catch (e: ApiException) {
        // Sign in was unsuccessful, handle the error
        AppPreference.saveString("token","123")
        onSuccess()
    }
}

private fun determineIfVolunteer(account: GoogleSignInAccount): Boolean {
    // Implement your logic to determine if the user is a volunteer
    return false // Example: return true if the user is a volunteer
}