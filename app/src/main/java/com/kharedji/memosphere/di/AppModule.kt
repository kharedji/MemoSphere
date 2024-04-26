package com.kharedji.memosphere.di

import android.app.Application
import androidx.room.Room
import com.kharedji.memosphere.data.data_source.NoteDatabase
import com.kharedji.memosphere.data.repository.NoteRepositoryImpl
import com.kharedji.memosphere.data.repository.UserRepositoryImpl
import com.kharedji.memosphere.domain.repository.NoteRepository
import com.kharedji.memosphere.domain.repository.UserRepository
import com.kharedji.memosphere.domain.use_case.AddNote
import com.kharedji.memosphere.domain.use_case.DeleteNote
import com.kharedji.memosphere.domain.use_case.GetNote
import com.kharedji.memosphere.domain.use_case.GetNotes
import com.kharedji.memosphere.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }

    @Provides
    fun provideSignUpRepository(): UserRepository {
        return UserRepositoryImpl()
    }
}