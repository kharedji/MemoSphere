package com.kharedji.memosphere.data.data_source.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kharedji.memosphere.domain.models.user.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "user_db"
    }
}