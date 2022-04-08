package com.patriciafiona.tentangku.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Menu
import com.patriciafiona.tentangku.databinding.FragmentHomeBinding
import com.patriciafiona.tentangku.ui.main.finance.FinanceActivity
import com.patriciafiona.tentangku.ui.main.notes.NotesActivity
import com.patriciafiona.tentangku.ui.main.reminder.ReminderActivity
import com.patriciafiona.tentangku.ui.main.weather.WeatherActivity
import com.patriciafiona.tentangku.ui.main.weight.WeightActivity


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
            "Notes" -> {
                val intent = Intent(requireActivity(), NotesActivity::class.java)
                startActivity(intent)
            }
            "Body Weight" -> {
                val intent = Intent(requireActivity(), WeightActivity::class.java)
                startActivity(intent)
            }
            "Finance" -> {
                val intent = Intent(requireActivity(), FinanceActivity::class.java)
                startActivity(intent)
            }
            "Reminder" -> {
                val intent = Intent(requireActivity(), ReminderActivity::class.java)
                startActivity(intent)
            }
            "Weather" -> {
                val intent = Intent(requireActivity(), WeatherActivity::class.java)
                startActivity(intent)
            }
        }
    }

}