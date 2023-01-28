package com.patriciafiona.tentangku.ui.main.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.ui.main.ui.theme.Boulder
import com.patriciafiona.tentangku.ui.widgets.Loader
import com.patriciafiona.tentangku.ui.widgets.LocationPermission
import com.patriciafiona.tentangku.utils.Utils
import com.patriciafiona.tentangku.utils.Utils.setBackgroundBaseOnTime
import java.util.*

private lateinit var viewModel: WeatherViewModel

//Location section
@SuppressLint("StaticFieldLeak")
private var fusedLocationProvider: FusedLocationProviderClient? = null
private val locationRequest: LocationRequest = LocationRequest.create().apply {
    interval = 30
    fastestInterval = 10
    priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    maxWaitTime = 60
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen (navController: NavController) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Boulder
    )

    //get lat long
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val isLoading = remember{ mutableStateOf(true) }

    //Prepare for location detection
    val lat = remember{ mutableStateOf(0.0) }
    val lon = remember{ mutableStateOf(0.0) }

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                //The last location in the list is the newest
                val location = locationList.last()
                lat.value = location.latitude
                lon.value = location.longitude

                isLoading.value = false
            }
        }
    }
    viewModel = WeatherViewModel(lat.value, lon.value)

    var latLongBoundary by remember { mutableStateOf("") }
    val cityName = remember{ mutableStateOf("") }
    val currentTemperature = remember{ mutableStateOf("") }
    val weatherType = remember{ mutableStateOf("") }
    val windVal = remember{ mutableStateOf("") }
    val humidityVal = remember{ mutableStateOf("") }
    val pressureVal = remember{ mutableStateOf("") }
    val lastUpdateVal = remember{ mutableStateOf("") }
    val weatherIconStatus = remember{ mutableStateOf("") }

    //get data from API
    viewModel.weather.observe(lifecycleOwner) { data ->
        cityName.value = data.name.toString()
        currentTemperature.value = "${data.main?.temp.toString()} \u2103"
        weatherType.value = "${data.weather?.get(0)?.main.toString()} - ${data.weather?.get(0)?.description.toString()}"

        windVal.value = "${data.wind?.speed.toString()} m/s"
        humidityVal.value = "${data.main?.humidity.toString()}%"
        pressureVal.value = "${data.main?.pressure.toString()} hPa"
        lastUpdateVal.value = "${data.dt?.toLong()
            ?.let { Utils.getDateFromTimemillis(it*1000, "EEE, d MMM yyyy HH:mm:ss") }}"

        weatherIconStatus.value = "https://openweathermap.org/img/wn/${data.weather?.get(0)?.icon.toString()}@4x.png"
    }

    OnLifecycle(
        context = context,
        locationCallback = locationCallback
    )

    viewModel = WeatherViewModel(
        lat.value,
        lon.value
    )

    //View Section
    if (locationPermissionsState.allPermissionsGranted) {
        if(!isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
            ) {
                Image(
                    painter = rememberDrawablePainter(
                        drawable = AppCompatResources.getDrawable(
                            LocalContext.current,
                            setBackgroundBaseOnTime()
                        )
                    ),
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    IconButton(
                        modifier =Modifier
                            .padding(top = 12.dp),
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Menu icon",
                            tint = if(Utils.getTimeGreetingStatus() != "Good Night") { Color.Black } else { Color.White }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = cityName.value,
                        style = TextStyle(
                            color = if (Utils.getTimeGreetingStatus() != "Good Night") {
                                Color.Black
                            } else {
                                Color.White
                            },
                            fontSize = 16.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(weatherIconStatus.value)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.weather_icon_api),
                                contentDescription = stringResource(R.string.description),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(3f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.today),
                                style = TextStyle(
                                    color = if (Utils.getTimeGreetingStatus() != "Good Night") {
                                        Color.Black
                                    } else {
                                        Color.White
                                    },
                                    fontSize = 18.sp
                                )
                            )
                            Text(
                                text = currentTemperature.value,
                                style = TextStyle(
                                    color = if (Utils.getTimeGreetingStatus() != "Good Night") {
                                        Color.Black
                                    } else {
                                        Color.White
                                    },
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = weatherType.value,
                                style = TextStyle(
                                    color = if (Utils.getTimeGreetingStatus() != "Good Night") {
                                        Color.Black
                                    } else {
                                        Color.White
                                    },
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        stringResource(id = R.string.last_update),
                        style = TextStyle(
                            color = if(Utils.getTimeGreetingStatus() != "Good Night") { Color.Black } else { Color.White },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = lastUpdateVal.value,
                        style = TextStyle(
                            color = if(Utils.getTimeGreetingStatus() != "Good Night") { Color.Black } else { Color.White },
                            fontSize = 12.sp,
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = 5.dp,
                        backgroundColor = Color.White,
                         shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            weatherStatus(
                                modifier = Modifier
                                    .weight(1f),
                                value = windVal.value,
                                desc = stringResource(id = R.string.wind),
                                icon = R.drawable.wind_icon
                            )

                            weatherStatus(
                                modifier = Modifier
                                    .weight(1f),
                                value = humidityVal.value,
                                desc = stringResource(id = R.string.humidity),
                                icon = R.drawable.humidity_icon
                            )

                            weatherStatus(
                                modifier = Modifier
                                    .weight(1f),
                                value = pressureVal.value,
                                desc = stringResource(id = R.string.pressure),
                                icon = R.drawable.pressure_icon
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Map(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        lat = lat,
                        lon = lon
                    ) { latLongBound ->
                        latLongBoundary = latLongBound.toString()
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(id = R.string.powered_by_openweathermap_org),
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    )
                }
            }
        }else{
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
        }
    }else {
        LocationPermission(locationPermissionsState = locationPermissionsState)
    }
}

@Composable
fun weatherStatus(
    modifier: Modifier,
    value: String,
    desc: String,
    icon: Int
) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(30.dp),
            painter = painterResource(id = icon),
            alpha = .3f,
            contentDescription = "Weather status icon"
        )
        Text(
            text = value,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        )
        Text(
            text = desc,
            style = TextStyle(
                color = Color.Gray,
                fontSize = 10.sp
            )
        )
    }
}

@Composable
fun Map(
    modifier: Modifier,
    lat: MutableState<Double>,
    lon: MutableState<Double>,
    onBoundsChange: (LatLngBounds?) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(lat.value, lon.value),
            16f
        )
    }

    // whenever cameraPositionState.isMoving changes, launch a coroutine
    //    to fire onBoundsChange. We'll report the visibleRegion
    //    LatLngBounds
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            onBoundsChange(
                cameraPositionState.projection?.visibleRegion?.latLngBounds
            )
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,

        // pass in an onMapLoaded to report the initial visible region
        onMapLoaded = {
            onBoundsChange(
                cameraPositionState.projection?.visibleRegion?.latLngBounds
            )
        }
    ){
        Marker(
            state = MarkerState(position = LatLng(lat.value, lon.value)),
            title = "My position"
        )
    }
}

@Composable
private fun OnLifecycle(
    context: Context,
    locationCallback: LocationCallback
) {
    Utils.OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationProvider?.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                }
            }
            Lifecycle.Event.ON_PAUSE -> {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationProvider?.removeLocationUpdates(locationCallback)
                }
            }
            else -> { /* other stuff */
            }
        }
    }
}