package com.kharedji.memosphere.presentation.screens.note

import com.kharedji.memosphere.domain.models.notes.Note
import com.kharedji.memosphere.domain.utils.NoteOrder


sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
