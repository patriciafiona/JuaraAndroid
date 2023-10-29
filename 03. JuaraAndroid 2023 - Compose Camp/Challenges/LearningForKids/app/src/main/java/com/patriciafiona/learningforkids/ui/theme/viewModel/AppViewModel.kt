package com.patriciafiona.learningforkids.ui.theme.viewModel

import android.content.Context
import android.speech.tts.TextToSpeech
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
import com.patriciafiona.learningforkids.data.Alphabet
import java.util.Locale

class AppViewModel: ViewModel() {
    private val database = Firebase.database

    val alphabets = mutableStateListOf<Alphabet>()
    var selectedData = mutableStateOf(Alphabet())

    init {
        getListAlphabet()
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
}