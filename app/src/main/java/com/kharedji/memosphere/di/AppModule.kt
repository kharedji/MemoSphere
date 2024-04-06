package com.kharedji.memosphere.di

import com.kharedji.memosphere.data.repository.UserRepositoryImpl
import com.kharedji.memosphere.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideSignUpRepository(): UserRepository {
        return UserRepositoryImpl()
    }
}