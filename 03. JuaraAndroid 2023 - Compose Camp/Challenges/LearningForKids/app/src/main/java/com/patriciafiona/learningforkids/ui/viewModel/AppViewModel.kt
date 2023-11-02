package com.patriciafiona.learningforkids.ui.viewModel

import android.os.Handler
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AppViewModel: ViewModel() {
    private val database = Firebase.database

    val alphabets = mutableStateListOf<Alphabet>()

    val colors = mutableStateListOf<ColorData>()
    var selectedColorData = mutableStateOf(ColorData())
    var isDetailReady = mutableStateOf(false)
    var isDetailLoading = mutableStateOf(false)

    private val _uiState = MutableStateFlow(
        AnimalAlphabetUiState(
            alphabetsList = alphabets,
            currentAlphabet = Alphabet(),
        )
    )
    val uiState: MutableStateFlow<AnimalAlphabetUiState> = _uiState

    init {
        getListAlphabet()
        getListColor()
    }

    fun updateCurrentAlphabet(selectedAlphabet: Alphabet) {
        _uiState.update {
            it.copy(currentAlphabet = selectedAlphabet)
        }

        isDetailLoading.value = true
        Handler().postDelayed({
            isDetailReady.value = true
            isDetailLoading.value = false
        }, 1000)
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }


    fun navigateToDetailPage(selectedData: Alphabet) {
        _uiState.update {
            it.copy(isShowingListPage = false, currentAlphabet = selectedData)
        }
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

data class AnimalAlphabetUiState(
    val alphabetsList: List<Alphabet> = emptyList(),
    val currentAlphabet: Alphabet = Alphabet(),
    val isShowingListPage: Boolean = true
)