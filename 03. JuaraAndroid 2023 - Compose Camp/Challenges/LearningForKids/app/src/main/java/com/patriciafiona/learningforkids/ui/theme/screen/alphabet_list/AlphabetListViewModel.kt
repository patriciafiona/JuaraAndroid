package com.patriciafiona.learningforkids.ui.theme.screen.alphabet_list

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.patriciafiona.learningforkids.data.Alphabet

private val TAG = "AlphabetListViewModel"
class AlphabetListViewModel: ViewModel() {
    private val database = Firebase.database

    val alphabets = mutableStateListOf<Alphabet>()

    fun getListAlphabet() {
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
                                img_url = value.img_url
                            )
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }
}