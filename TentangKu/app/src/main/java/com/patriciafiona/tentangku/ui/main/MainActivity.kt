package com.patriciafiona.tentangku.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
import com.patriciafiona.tentangku.databinding.ActivitySignInBinding
import com.patriciafiona.tentangku.ui.main.about.AboutActivity
import com.patriciafiona.tentangku.ui.signin.SignInActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var doubleBackToExitPressedOnce = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            mAuth = FirebaseAuth.getInstance()

            //Setting drawer for menu
            setMenuDrawer()

            binding.menuButton.setOnClickListener {
                drawerLayout?.openDrawer(Gravity.LEFT)
            }
        } else {
            // No user is signed in
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        //Setting drawer for menu
        setMenuDrawer()

        binding.menuButton.setOnClickListener {
            drawerLayout?.openDrawer(Gravity.LEFT)
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            exitProcess(0)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.press_again, Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun setMenuDrawer(){
        drawerLayout = findViewById<View>(R.id.activity_main) as DrawerLayout
        navigationView = findViewById<View>(R.id.nv) as NavigationView

        //change username and email in drawer list
        val navigationHeader = navigationView!!.getHeaderView(0)

        val user = Firebase.auth.currentUser
        user?.let {
            Glide.with(this)
                .load(user.photoUrl)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(navigationHeader.findViewById(R.id.drawerHeaderPhoto))

            navigationHeader.findViewById<TextView>(R.id.drawerHeaderName).text = user.displayName
            navigationHeader.findViewById<TextView>(R.id.drawerHeaderEmail).text = user.email
        }

        navigationView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawerAbout -> showAboutPage()
                R.id.drawerLogout -> signOut()
                else -> return@OnNavigationItemSelectedListener true
            }
            true
        })
    }

    private fun showAboutPage(){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)

        //close drawer
        drawerLayout = findViewById<View>(R.id.activity_main) as DrawerLayout
        drawerLayout?.closeDrawer(Gravity.LEFT, false)
    }

    private fun signOut(){
        mAuth.signOut()

        //Back to Sign in Page
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}