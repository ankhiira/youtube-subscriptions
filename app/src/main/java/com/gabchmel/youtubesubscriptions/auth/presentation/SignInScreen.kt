package com.gabchmel.youtubesubscriptions.auth.presentation

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gabchmel.youtubesubscriptions.auth.GoogleAuthProvider
import com.gabchmel.youtubesubscriptions.auth.GoogleAuthUiProvider.Companion.WEB_CLIENT_ID
import com.gabchmel.youtubesubscriptions.auth.model.GoogleAccount
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import kotlin.jvm.java

interface GoogleButtonClick {
    fun onSignInClicked()

    fun onSignOutClicked()
}

@Composable
fun SignInScreen() {

    var signedInUser by remember { mutableStateOf<GoogleSignInAccount?>(null) }
    val context = LocalContext.current

    val googleSignInOptions = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestScopes(Scope("https://www.googleapis.com/auth/youtube.readonly"))
            .requestEmail()
            .requestProfile()
            .build()
    }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, googleSignInOptions)
    }

    // Activity Result Launcher for the Google Sign-In Intent
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("GoogleSignIn", "Sign-in successful for ${account.email}")
                // The idToken is what you'd send to your backend server.
                Log.d("GoogleSignIn", "ID Token: ${account.idToken}")
                signedInUser = account
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "Sign-in failed: ${e.statusCode}")
            }
        }
    }

    // Check for an already signed-in user on initial composition.
    LaunchedEffect(key1 = Unit) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            Log.d("GoogleSignIn", "Found already signed-in user")
            signedInUser = account
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (signedInUser == null) {
            Column {
                Button(onClick = {
                    val signInIntent = googleSignInClient.signInIntent
                    signInLauncher.launch(signInIntent)
                }) {
                    Text("Sign in with Google")
                }

                GoogleSignInButton(
                    onGoogleSignInResult = {}
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                AsyncImage(
//                    model = signedInUser?.photoUrl,
//                    contentDescription = "User Profile Photo",
//                    modifier = Modifier.size(96.dp)
//                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome, ${signedInUser?.displayName}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(text = "${signedInUser?.email}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = {
                    googleSignInClient.signOut().addOnCompleteListener {
                        Log.d("GoogleSignIn", "Sign-out successful")
                        signedInUser = null
                    }
                }) {
                    Text("Sign Out")
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onGoogleSignInResult: (GoogleAccount?) -> Unit
) {
    val googleAuthProvider = koinInject<GoogleAuthProvider>()
    val googleAuthUiProvider = googleAuthProvider.getUiProvider()
    val coroutineScope = rememberCoroutineScope()
    val uiContainerScope =
        remember {
            object : GoogleButtonClick {
                override fun onSignInClicked() {
                    coroutineScope.launch {
                        val googleUser = googleAuthUiProvider.signIn()
                        onGoogleSignInResult(googleUser)
                    }
                }


                override fun onSignOutClicked() {
                    coroutineScope.launch {
                        googleAuthProvider.signOut()
                    }
                }
            }
        }

    OutlinedButton(
        modifier = modifier,
        onClick = { uiContainerScope.onSignInClicked() },
        content = {
            Text("Login")
        }
    )
}