package com.patriciafiona.tentangku.ui.signin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.data.source.remote.responses.UserResponse
import com.patriciafiona.tentangku.databinding.ActivitySignInBinding
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.MainActivity
import com.patriciafiona.tentangku.ui.main.TentangKuApp
import com.patriciafiona.tentangku.ui.main.ui.theme.*

private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var googleSignInClient: GoogleSignInClient
private lateinit var database: DatabaseReference
private const val TAG = "GoogleSignInActivity"
private const val RC_SIGN_IN = 9001

@Composable
fun SignInScreen (navController: NavController) {
    val activity = LocalContext.current as Activity

    onLifecycle(activity = activity, navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Beeswax),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.signin_bg),
            contentScale = ContentScale.Crop,
            contentDescription = "Sign In Image"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sign in",
            style = TextStyle(
                color = Sepia,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(.6f),
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(activity, signInIntent, RC_SIGN_IN, null)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = DimGray)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .size(20.dp)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberDrawablePainter(
                            drawable = AppCompatResources.getDrawable(LocalContext.current, R.drawable.google_logo)
                        ),
                        contentScale = ContentScale.Fit,
                        contentDescription = "Google icon"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.sign_in_with_google),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun onLifecycle(activity: Activity, navController: NavController) {
    OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                // Initialize Firebase Auth & Database
                auth = Firebase.auth
                database = Firebase.database.reference

                // Configure Google Sign In
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                googleSignInClient = GoogleSignIn.getClient(activity, gso)
            }
            Lifecycle.Event.ON_START -> {
                // Check if user is signed in (non-null) and update UI accordingly.
                val currentUser = auth.currentUser
                updateUI(currentUser, navController)
            }
            else -> { /* other stuff */ }
        }
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

private fun firebaseAuthWithGoogle(idToken: String, activity: Activity, navController: NavController) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                updateUI(user, navController)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                updateUI(null, navController)
            }
        }
}

@SuppressLint("SimpleDateFormat")
private fun writeNewUserLogin(user: FirebaseUser) {
    val userName = user.displayName
    val userEmail = user.email

    if (!userName.isNullOrEmpty() && !userEmail.isNullOrEmpty()) {
        val userData = UserResponse(
            userName,
            userEmail,
            Utils.getCurrentDate("datetime")
        )

        database.child("users").child(userEmail.replace(".", ",")).setValue(userData)
    }else{
        Log.e("Status", "Can't Added to Database! Null User Data")
    }
}

private fun updateUI(user: FirebaseUser?, navController: NavController) {
    if (user != null) {
        writeNewUserLogin(user)
        Log.e("Status", "Updating UI")

        //Go to Main Screen
        navController.navigate(TentangkuScreen.MainScreen.route)
    }else{
        Log.e("Status", "Null User Data")
    }
}