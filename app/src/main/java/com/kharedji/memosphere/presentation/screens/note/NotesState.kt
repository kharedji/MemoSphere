package com.kharedji.memosphere.presentation.screens.note

import com.kharedji.memosphere.domain.models.notes.Note
import com.kharedji.memosphere.domain.utils.NoteOrder
import com.kharedji.memosphere.domain.utils.OrderType


data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
