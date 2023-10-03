package com.patriciafiona.tentangku.ui.main.notes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.repository.NoteRepository

class NoteViewModel (application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}