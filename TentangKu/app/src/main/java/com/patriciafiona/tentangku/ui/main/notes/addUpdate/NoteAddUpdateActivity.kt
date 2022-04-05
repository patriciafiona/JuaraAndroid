package com.patriciafiona.tentangku.ui.main.notes.addUpdate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.databinding.ActivityNoteAddUpdateBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import java.util.*


class NoteAddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteAddUpdateBinding

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var isEdit = false
    private var note: Note? = null

    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAddUpdateViewModel = obtainViewModel(this@NoteAddUpdateActivity)

        with(binding){
            textInputLayoutTitle.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
            textInputLayoutDescription.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE

            btnSaveUpdate.isEnabled = false

            if (isEdit) {
                if (note != null) {
                    note?.let { note ->
                        edtTitle.setText(note.title)
                        edtDescription.setText(note.description)
                        noteDate.text = note.date
                        btnDelete.isVisible = true
                    }
                }
            }else{
                noteDate.text = Utils.getCurrentDate("date")
                btnDelete.isVisible = false
            }

            btnSaveUpdate.setOnClickListener {
                Log.e("Add Edit Notes", "Success")
            }
            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            edtTitle.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    if (s.isNotEmpty()) {
                        btnSaveUpdate.isEnabled = !edtDescription.text.isNullOrEmpty()
                    }else{
                        btnSaveUpdate.isEnabled = false
                    }
                }
            })

            edtDescription.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    if (s.isNotEmpty()) {
                        btnSaveUpdate.isEnabled = !edtTitle.text.isNullOrEmpty()
                    }else{
                        btnSaveUpdate.isEnabled = false
                    }
                }
            })

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }

}