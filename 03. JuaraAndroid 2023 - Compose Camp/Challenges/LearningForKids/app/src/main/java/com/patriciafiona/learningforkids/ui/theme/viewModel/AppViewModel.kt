package com.patriciafiona.learningforkids.ui.theme.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.patriciafiona.learningforkids.data.entity.Alphabet
import com.patriciafiona.learningforkids.data.entity.ColorData

class AppViewModel: ViewModel() {
    private val database = Firebase.database

    val alphabets = mutableStateListOf<Alphabet>()
    var selectedData = mutableStateOf(Alphabet())

    val colors = mutableStateListOf<ColorData>()
    var selectedColorData = mutableStateOf(ColorData())

    init {
        getListAlphabet()
        getListColor()
    }

    private fun getListAlphabet() {
        alphabets.clear()

        val myRef = database.getReference("animal_alphabets")

        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue<HashMap<String, Alphabet>>()

                if (data != null) {
                    for ((key, value) in data) {
                        alphabets.add(
                            Alphabet(
                                name = value.name,
                                color = value.color,
                                color_dark = value.color_dark,
                                img_url = value.img_url,
                                phonic = value.phonic,
                                videoId = value.videoId
                            )
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ViewModel", "Failed to read value.", error.toException())
            }

        })
    }

    private fun getListColor() {
        colors.clear()

        val myRef = database.getReference("colors")

        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue<HashMap<String, ColorData>>()

                if (data != null) {
                    for ((key, value) in data) {
                        colors.add(
                            ColorData(
                                name = value.name,
                                color = value.color,
                                images = value.images
                            )
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ViewModel", "Failed to read value.", error.toException())
            }

        })
    }
}