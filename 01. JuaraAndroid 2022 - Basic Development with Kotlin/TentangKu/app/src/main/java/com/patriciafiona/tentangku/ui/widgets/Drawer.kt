package com.patriciafiona.tentangku.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.helper.DrawerScreens
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val drawerScreens = listOf(
    DrawerScreens.About,
    DrawerScreens.SignOut,
)

@Composable
fun DrawerContent(
    navController: NavController,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    googleSignInClient: GoogleSignInClient,
    mAuth: FirebaseAuth,
    userFirebase: FirebaseUser
) {
    Column (
        modifier = Modifier
            .width(300.dp)
            .padding(start = 24.dp, top = 48.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userFirebase.photoUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_person_gray),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(3.dp, Color.LightGray, CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        userFirebase.displayName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h6,
                color = Color.LightGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        userFirebase.email?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        drawerScreens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if(screen.title == "About") {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }

                            navController.navigate(TentangkuScreen.AboutScreen.route)
                        }else{
                            googleSignInClient.signOut().addOnSuccessListener {
                                mAuth.signOut()

                                //Back to Sign in Page
                                navController.navigate(TentangkuScreen.SignInScreen.route) {
                                    popUpTo(TentangkuScreen.SignInScreen.route) { inclusive = true }
                                }
                            }
                        }
                    }
            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "Drawer icon",
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = screen.title,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}