package com.kharedji.memosphere.di

import android.app.Application
import androidx.room.Room
import com.kharedji.memosphere.data.data_source.notes.NoteDatabase
import com.kharedji.memosphere.data.data_source.user.UserDatabase
import com.kharedji.memosphere.data.repository.BlogRepositoryImpl
import com.kharedji.memosphere.data.repository.NoteRepositoryImpl
import com.kharedji.memosphere.data.repository.UserRepositoryImpl
import com.kharedji.memosphere.domain.repository.BlogRepository
import com.kharedji.memosphere.domain.repository.NoteRepository
import com.kharedji.memosphere.domain.repository.UserRepository
import com.kharedji.memosphere.domain.use_case.blogs.AddBlog
import com.kharedji.memosphere.domain.use_case.blogs.AddImageToFirbase
import com.kharedji.memosphere.domain.use_case.blogs.BlogUseCases
import com.kharedji.memosphere.domain.use_case.blogs.GetBlogs
import com.kharedji.memosphere.domain.use_case.notes.AddNote
import com.kharedji.memosphere.domain.use_case.notes.DeleteNote
import com.kharedji.memosphere.domain.use_case.notes.GetNote
import com.kharedji.memosphere.domain.use_case.notes.GetNotes
import com.kharedji.memosphere.domain.use_case.notes.NoteUseCases
import com.kharedji.memosphere.domain.use_case.user.AddAvatarToFirebase
import com.kharedji.memosphere.domain.use_case.user.AddUser
import com.kharedji.memosphere.domain.use_case.user.AddUserToFirestore
import com.kharedji.memosphere.domain.use_case.user.DeleteUser
import com.kharedji.memosphere.domain.use_case.user.GetUser
import com.kharedji.memosphere.domain.use_case.user.UserUseCases
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
    @Singleton
    fun provideUserDataBase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideUserRepository(db: UserDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            addUser = AddUser(repository),
            addUserToFirestore = AddUserToFirestore(repository),
            getUser = GetUser(repository),
            addAvatarToFirebase = AddAvatarToFirebase(repository),
            deleteUser = DeleteUser(repository)
        )
    }

    @Provides
    @Singleton
    fun providesBlogRepository(): BlogRepository {
        return BlogRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesBlogUseCases(repository: BlogRepository): BlogUseCases {
        return BlogUseCases(
            addImageToFirbase = AddImageToFirbase(repository),
            getBlogs = GetBlogs(repository),
            addBlog = AddBlog(repository),
        )
    }
}