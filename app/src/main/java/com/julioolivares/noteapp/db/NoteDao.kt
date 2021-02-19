package com.julioolivares.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.julioolivares.noteapp.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete

    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
     fun getallNotes(): LiveData<List<Note>>

     @Query("SELECT * FROM notes where noteTitle like :query or noteBody like :query")
     fun searchNote(query: String?) : LiveData<List<Note>>
}