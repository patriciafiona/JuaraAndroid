package com.patriciafiona.tentangku.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var doubleBackToExitPressedOnce = false
    private lateinit var mAuth: FirebaseAuth

    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 30
        fastestInterval = 10
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        maxWaitTime = 60
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                //The last location in the list is the newest
                val location = locationList.last()
                Toast.makeText(
                    this@MainActivity,
                    "Got Location: $location",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
        private const val MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION = 66
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
//        checkLocationPermission()
//
//        val user = Firebase.auth.currentUser
//        if (user != null) {
//            // User is signed in
//            mAuth = FirebaseAuth.getInstance()
//
//            //Setting drawer for menu
//            setMenuDrawer()
//
//            binding.menuButton.setOnClickListener {
//                drawerLayout?.openDrawer(Gravity.LEFT)
//            }
//        } else {
//            // No user is signed in
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//        }
//
//        //Setting drawer for menu
//        setMenuDrawer()
//
//        binding.menuButton.setOnClickListener {
//            drawerLayout?.openDrawer(Gravity.LEFT)
//        }
    }

//    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed()
//            exitProcess(0)
//        }
//
//        this.doubleBackToExitPressedOnce = true
//        Toast.makeText(this, R.string.press_again, Toast.LENGTH_SHORT).show()
//
//        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
//    }
//
//    private fun setMenuDrawer(){
//        drawerLayout = findViewById<View>(R.id.activity_main) as DrawerLayout
//        navigationView = findViewById<View>(R.id.nv) as NavigationView
//
//        //change username and email in drawer list
//        val navigationHeader = navigationView!!.getHeaderView(0)
//
//        val user = Firebase.auth.currentUser
//        user?.let {
//            Glide.with(this)
//                .load(user.photoUrl)
//                .transform(RoundedCorners(20))
//                .apply(
//                    RequestOptions.placeholderOf(R.drawable.ic_loading)
//                        .error(R.drawable.ic_error)
//                )
//                .into(navigationHeader.findViewById(R.id.drawerHeaderPhoto))
//
//            navigationHeader.findViewById<TextView>(R.id.drawerHeaderName).text = user.displayName
//            navigationHeader.findViewById<TextView>(R.id.drawerHeaderEmail).text = user.email
//        }
//
//        navigationView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.drawerAbout -> showAboutPage()
//                R.id.drawerLogout -> signOut()
//                else -> return@OnNavigationItemSelectedListener true
//            }
//            true
//        })
//    }
//
//    private fun showAboutPage(){
//        val intent = Intent(this, AboutActivity::class.java)
//        startActivity(intent)
//
//        //close drawer
//        drawerLayout = findViewById<View>(R.id.activity_main) as DrawerLayout
//        drawerLayout?.closeDrawer(Gravity.LEFT, false)
//    }
//
//    private fun signOut(){
//        mAuth.signOut()
//
//        //Back to Sign in Page
//        val intent = Intent(this, SignInActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//
//            fusedLocationProvider?.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//
//            fusedLocationProvider?.removeLocationUpdates(locationCallback)
//        }
//    }
//
//    private fun checkLocationPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            ) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                AlertDialog.Builder(this)
//                    .setTitle("Location Permission Needed")
//                    .setMessage("This app needs the Location permission, please accept to use location functionality")
//                    .setPositiveButton(
//                        "OK"
//                    ) { _, _ ->
//                        //Prompt the user once explanation has been shown
//                        requestLocationPermission()
//                    }
//                    .create()
//                    .show()
//            } else {
//                // No explanation needed, we can request the permission.
//                requestLocationPermission()
//            }
//        } else {
//            checkBackgroundLocation()
//        }
//    }
//
//    private fun checkBackgroundLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestBackgroundLocationPermission()
//        }
//    }
//
//    private fun requestLocationPermission() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//            ),
//            MY_PERMISSIONS_REQUEST_LOCATION
//        )
//    }
//
//    private fun requestBackgroundLocationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                ),
//                MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION
//            )
//        } else {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                MY_PERMISSIONS_REQUEST_LOCATION
//            )
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            MY_PERMISSIONS_REQUEST_LOCATION -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        fusedLocationProvider?.requestLocationUpdates(
//                            locationRequest,
//                            locationCallback,
//                            Looper.getMainLooper()
//                        )
//
//                        // Now check background location
//                        checkBackgroundLocation()
//                    }
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
//
//                    // Check if we are in a state where the user has denied the permission and
//                    // selected Don't ask again
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        )
//                    ) {
//                        startActivity(
//                            Intent(
//                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                                Uri.fromParts("package", this.packageName, null),
//                            ),
//                        )
//                    }
//                }
//                return
//            }
//            MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        fusedLocationProvider?.requestLocationUpdates(
//                            locationRequest,
//                            locationCallback,
//                            Looper.getMainLooper()
//                        )
//
//                        Toast.makeText(
//                            this,
//                            "Granted Background Location Permission",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
//                }
//                return
//
//            }
//        }
//    }
}