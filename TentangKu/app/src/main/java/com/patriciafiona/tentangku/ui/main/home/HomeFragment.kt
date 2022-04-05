package com.patriciafiona.tentangku.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Menu
import com.patriciafiona.tentangku.databinding.FragmentHomeBinding
import com.patriciafiona.tentangku.ui.main.notes.NotesActivity
import com.patriciafiona.tentangku.ui.main.weight.WeightActivity
import com.patriciafiona.tentangku.ui.signin.SignInActivity
import java.io.IOException
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private lateinit var rvMenu: RecyclerView
    private val list = ArrayList<Menu>()
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            val user = Firebase.auth.currentUser
            if (user != null) {
                userName.text = user.displayName
            }
            rvMenu = rvMainMenu
            rvMenu.setHasFixedSize(true)

            list.addAll(listMenus)
            showRecyclerList()
        }

    }

    private val listMenus: ArrayList<Menu>
        @SuppressLint("Recycle")
        get() {
            val dataName = resources.getStringArray(R.array.menu_name)
            val dataPhoto = resources.obtainTypedArray(R.array.menu_img)
            val listMenu = ArrayList<Menu>()
            for (i in dataName.indices) {
                val menu = Menu(dataName[i], dataPhoto.getResourceId(i, -1))
                listMenu.add(menu)
            }
            return listMenu
        }
    private fun showRecyclerList() {
        rvMenu.layoutManager = GridLayoutManager(requireActivity(), 5)
        val menuAdapter = MenuAdapter(list)
        rvMenu.adapter = menuAdapter

        menuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Menu) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(menu: Menu) {
        when(menu.name){
            "Notes" ->{
                val intent = Intent(requireActivity(), NotesActivity::class.java)
                startActivity(intent)
            }
            "Body Weight" ->{
                val intent = Intent(requireActivity(), WeightActivity::class.java)
                startActivity(intent)
            }
        }
    }

}