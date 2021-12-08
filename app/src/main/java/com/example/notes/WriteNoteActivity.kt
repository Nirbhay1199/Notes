package com.example.notes

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.WriteNotesBinding

class WriteNoteActivity: AppCompatActivity() {

    private lateinit var binding: WriteNotesBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = WriteNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]

//        binding.notesView.isSingleLine = false
//        binding.notesView.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
        binding.notesView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        binding.notesView.isVerticalScrollBarEnabled = true
        binding.notesView.movementMethod = ScrollingMovementMethod.getInstance()
        binding.notesView.scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
        binding.notesView.maxLines = 10
        binding.notesView.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        val saveData = binding.backSave
        saveData.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val noteText = binding.notesView.text.toString()
        if (noteText.isNotEmpty() and noteText.isNotBlank()){
            viewModel.insertNote(Note(noteText))
        }
        finish()
    }
}