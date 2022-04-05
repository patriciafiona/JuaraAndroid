package com.patriciafiona.tentangku.ui.main.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
import com.patriciafiona.tentangku.databinding.ActivityNotesBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val noteViewModel = obtainViewModel(this@NotesActivity)
            noteViewModel.getAllNotes().observe(this@NotesActivity) { noteList ->
                if (noteList != null  && noteList.isNotEmpty()) {
                    val sortedNoteList = noteList.sortedByDescending { note -> note.id }
                    adapter.setListNotes(sortedNoteList)
                    checkNotesAvailability(true)
                }else{
                    checkNotesAvailability(false)
                }
            }

            adapter = NoteAdapter()
            rvNotes.layoutManager = LinearLayoutManager(this@NotesActivity)
            rvNotes.setHasFixedSize(true)
            rvNotes.adapter = adapter

            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            btnAddNote.setOnClickListener {
                val intent = Intent(this@NotesActivity, NoteAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteViewModel::class.java)
    }

    private fun checkNotesAvailability(status: Boolean){
        with(binding){
            rvNotes.isVisible = status
            noFileImg.isVisible = !status
            noFileTxt.isVisible = !status
        }
    }
}