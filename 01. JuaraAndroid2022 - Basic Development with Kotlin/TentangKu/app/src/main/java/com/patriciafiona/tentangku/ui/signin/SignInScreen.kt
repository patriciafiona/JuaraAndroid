package com.patriciafiona.tentangku.ui.signin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.remote.responses.UserResponse
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.*
import com.patriciafiona.tentangku.ui.widgets.Loader
import com.patriciafiona.tentangku.utils.Utils
import com.patriciafiona.tentangku.utils.Utils.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var googleSignInClient: GoogleSignInClient
private lateinit var database: DatabaseReference
private const val TAG = "GoogleSignInActivity"

private lateinit var startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen (navController: NavController) {
    val activity = LocalContext.current as Activity
    val isLoading = remember{ mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (result.data != null) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(
                        account.idToken!!,
                        activity = activity,
                        navController = navController,
                        isLoading = isLoading,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Google sign in failed"
                        )
                    }

                    Log.e(TAG, "Google sign in failed", e)
                }
            }
        }
    }

    OnLifecycle(
        activity = activity,
        navController,
        isLoading = isLoading,
        scope = scope,
        snackbarHostState = snackbarHostState
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Beeswax),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isLoading.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray.copy(alpha = .8f))
                ){
                    Loader(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }else {
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
                        .fillMaxWidth(.7f),
                    onClick = {
                        isLoading.value = true
                        startForResult.launch(googleSignInClient.signInIntent)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = DimGray)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = rememberDrawablePainter(
                                    drawable = AppCompatResources.getDrawable(
                                        LocalContext.current,
                                        R.drawable.google_logo
                                    )
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
    }
}

@Composable
fun OnLifecycle(
    activity: Activity,
    navController: NavController,
    isLoading: MutableState<Boolean>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
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
            Lifecycle.Event.ON_RESUME -> {
                isLoading.value = true
                // Check if user is signed in (non-null) and update UI accordingly.
                val currentUser = auth.currentUser
                updateUI(
                    currentUser,
                    navController,
                    isLoading = isLoading,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
            else -> { /* other stuff */ }
        }
    }
}

private fun firebaseAuthWithGoogle(
    idToken: String,
    activity: Activity,
    navController: NavController,
    isLoading: MutableState<Boolean>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    isLoading.value = true
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                updateUI(
                    user,
                    navController,
                    isLoading = isLoading,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                updateUI(
                    null,
                    navController,
                    isLoading = isLoading,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
}

@SuppressLint("SimpleDateFormat")
private fun writeNewUserLogin(
    user: FirebaseUser,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
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
        scope.launch {
            snackbarHostState.showSnackbar(
                "Can't Added to Database! Null User Data"
            )
        }
        Log.e("Status", "Can't Added to Database! Null User Data")
    }
}

private fun updateUI(
    user: FirebaseUser?,
    navController: NavController,
    isLoading: MutableState<Boolean>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    if (user != null) {
        writeNewUserLogin(
            user,
            snackbarHostState,
            scope
        )
        Log.e("Status", "Updating UI")

        //Go to Main Screen
        navController.navigate(TentangkuScreen.HomeScreen.route) {
            popUpTo(TentangkuScreen.SignInScreen.route) { inclusive = true }
        }
        isLoading.value = false
    }else{
        isLoading.value = false

        Log.e("Status", "Null User Data")
    }
}