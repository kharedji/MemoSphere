package com.kharedji.memosphere.domain.use_case.notes

import com.kharedji.memosphere.domain.models.notes.InvalidNoteException
import com.kharedji.memosphere.domain.models.notes.Note
import com.kharedji.memosphere.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}