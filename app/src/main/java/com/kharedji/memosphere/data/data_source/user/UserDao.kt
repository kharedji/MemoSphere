package com.kharedji.memosphere.data.data_source.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kharedji.memosphere.domain.models.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<User>
}