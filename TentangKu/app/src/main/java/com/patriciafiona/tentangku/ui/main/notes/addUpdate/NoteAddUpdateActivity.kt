package com.patriciafiona.tentangku.ui.main.notes.addUpdate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.databinding.ActivityNoteAddUpdateBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import java.nio.file.Files.delete
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

            note = intent.getParcelableExtra(EXTRA_NOTE)
            if (note != null) {
                isEdit = true
            } else {
                note = Note()
            }

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
                val title = edtTitle.text.toString().trim()
                val description = edtDescription.text.toString().trim()
                when {
                    title.isEmpty() -> {
                        edtTitle.error = getString(R.string.empty)
                    }
                    description.isEmpty() -> {
                        edtDescription.error = getString(R.string.empty)
                    }
                    else -> {
                        note.let { note ->
                            note?.title = title
                            note?.description = description
                        }
                        if (isEdit) {
                            noteAddUpdateViewModel.update(note as Note)
                            showToast(getString(R.string.changed))
                        } else {
                            note.let { note ->
                                note?.date = Utils.getCurrentDate("date")
                            }
                            noteAddUpdateViewModel.insert(note as Note)
                            showToast(getString(R.string.added))
                        }
                        finish()
                    }
                }
            }
            btnBack.setOnClickListener {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
            btnDelete.setOnClickListener {
                showAlertDialog(ALERT_DIALOG_DELETE)
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    noteAddUpdateViewModel.delete(note as Note)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}