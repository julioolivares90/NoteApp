package com.julioolivares.noteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.julioolivares.noteapp.model.Note
import com.julioolivares.noteapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
        app: Application,
        private val noteRepository: NoteRepository) : AndroidViewModel(app) {
            fun addNote(note: Note) = viewModelScope.launch {
                noteRepository.addNote(note)
            }
    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun getAllNotes() = noteRepository.getAllNotes()
}