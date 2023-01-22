package com.patriciafiona.tentangku.ui.main.home

import android.Manifest
import android.accounts.Account
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.Help
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.request.ImageRequest
import com.github.mikephil.charting.charts.BarChart
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.internal.service.Common
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Menu
import com.patriciafiona.tentangku.helper.PermissionAction
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.WhiteSmoke
import kotlinx.coroutines.launch

private lateinit var mAuth: FirebaseAuth
private var fusedLocationProvider: FusedLocationProviderClient? = null
private val locationRequest: LocationRequest = LocationRequest.create().apply {
    interval = 30
    fastestInterval = 10
    priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    maxWaitTime = 60
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController){
    val context = LocalContext.current

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )

    val dataName = stringArrayResource(R.array.menu_name)
    val imageRes = arrayOf(
        R.drawable.weight_icon,
        R.drawable.finance_icon,
        R.drawable.notes_icon,
        R.drawable.reminder_icon,
        R.drawable.weather_icon
    )
    val listMenu = ArrayList<Menu>()
    for (i in dataName.indices) {
        val menu = Menu(dataName[i], imageRes[i])
        listMenu.add(menu)
    }

    //Drawer state
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                //The last location in the list is the newest
                val location = locationList.last()
                Toast.makeText(
                    context,
                    "Got Location: $location",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
    //Check location permission

    val user = Firebase.auth.currentUser
    if (user != null) {
        // User is signed in
        mAuth = FirebaseAuth.getInstance()
    } else {
        // No user is signed in
        navController.navigate(TentangkuScreen.SignInScreen.route)
    }


    //View Section
    if (locationPermissionsState.allPermissionsGranted) {
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    backgroundColor = WhiteSmoke,
                    elevation = 0.dp
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu icon",
                            tint = Color.LightGray
                        )
                    }
                }
            },
            drawerGesturesEnabled = true,
            drawerContent = {
                DrawerContent()
            },
            drawerBackgroundColor = Color.White
        ) {
            BackdropScaffold(
                scaffoldState = backdropScaffoldState,
                modifier = Modifier
                    .padding(it),
                appBar = {},
                backLayerContent = {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.home_bg),
                                contentDescription = "Home Background",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.wave_gray),
                                contentDescription = "Wave",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                },
                frontLayerContent = {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.menu),
                            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Card(
                            elevation = 5.dp,
                            modifier = Modifier
                                .height(220.dp)
                        ) {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .background(Color.White),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                columns = GridCells.Adaptive(minSize = 70.dp)
                            ) {
                                items(listMenu) { menu ->
                                    Column(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .weight(1f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Card(
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.size(50.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = menu.icon),
                                                contentDescription = "Menu icon",
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = menu.name,
                                            style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }

                        Row(modifier = Modifier.padding(top = 20.dp)) {
                            Text(
                                modifier = Modifier
                                    .weight(.8f),
                                text = stringResource(id = R.string.my_finance),
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                            )
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForwardIos,
                                    contentDescription = "My Finance button"
                                )
                            }
                        }

                        AndroidView(
                            factory = { context ->
                                val view = LayoutInflater.from(context)
                                    .inflate(R.layout.bar_chart_finance, null, false)
                                val barChart = view.findViewById<BarChart>(R.id.finance_balance_chart)

                                view // return the view
                            }
                        )

                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = stringResource(id = R.string.this_is_your_finance_summary_in_this_month),
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )

                    }
                },
                // Defaults to BackdropScaffoldDefaults.PeekHeight
                peekHeight = 400.dp,
                // Defaults to BackdropScaffoldDefaults.HeaderHeight
                headerHeight = 60.dp,
                // Defaults to true
                gesturesEnabled = false
            )
        }
    }else {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val allPermissionsRevoked =
                locationPermissionsState.permissions.size ==
                        locationPermissionsState.revokedPermissions.size

            val textToShow = if (!allPermissionsRevoked) {
                // If not all the permissions are revoked, it's because the user accepted the COARSE
                // location permission, but not the FINE one.
                "Yay! Thanks for letting me access your approximate location. " +
                        "But you know what would be great? If you allow me to know where you " +
                        "exactly are. Thank you!"
            } else if (locationPermissionsState.shouldShowRationale) {
                // Both location permissions have been denied
                "Getting your exact location is important for this app. " +
                        "Please grant us fine location. Thank you :D"
            } else {
                // First time the user sees this feature or the user doesn't want to be asked again
                "This feature requires location permission"
            }

            val buttonText = if (!allPermissionsRevoked) {
                "Allow precise location"
            } else {
                "Request permissions"
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth(.7f),
                painter = painterResource(id = R.drawable.location_permission),
                contentDescription = "Location permission image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = textToShow)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text(buttonText)
            }
        }
    }
}

sealed class DrawerScreens(val title: String, val icon: ImageVector) {
    object About : DrawerScreens("About", Icons.Default.Info)
    object SignOut : DrawerScreens("Sign Out", Icons.Default.Lock)
}

private val drawerScreens = listOf(
    DrawerScreens.About,
    DrawerScreens.SignOut,
)

@Composable
fun DrawerContent() {
    val user = Firebase.auth.currentUser

    Column (
        modifier = Modifier
            .width(300.dp)
            .padding(start = 24.dp, top = 48.dp)
    ) {
        if (user != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.photoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_person_gray),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
        }

        if (user != null) {
            user.displayName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h5,
                    color = Color.LightGray
                )
            }
        }
        if (user != null) {
            user.email?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
        }

        drawerScreens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
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

@Preview(device = Devices.PIXEL_2_XL, showBackground = true)
@Preview(device = Devices.PIXEL_4, showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}