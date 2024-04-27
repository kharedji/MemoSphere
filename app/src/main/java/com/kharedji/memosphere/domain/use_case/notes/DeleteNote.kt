package com.kharedji.memosphere.domain.use_case.notes

import com.kharedji.memosphere.domain.models.notes.Note
import com.kharedji.memosphere.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}