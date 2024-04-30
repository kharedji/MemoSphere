package com.kharedji.memosphere.domain.use_case.user

import com.kharedji.memosphere.domain.repository.UserRepository

class GetUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.getUser()
}