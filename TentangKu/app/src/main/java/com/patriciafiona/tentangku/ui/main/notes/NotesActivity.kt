package com.patriciafiona.tentangku.ui.main.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
import com.patriciafiona.tentangku.databinding.ActivityNotesBinding
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNotesAvailability(false)

        with(binding){
            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            btnAddNote.setOnClickListener {
                val intent = Intent(this@NotesActivity, NoteAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkNotesAvailability(status: Boolean){
        with(binding){
            rvNotes.isVisible = status
            noFileImg.isVisible = !status
            noFileTxt.isVisible = !status
        }
    }
}