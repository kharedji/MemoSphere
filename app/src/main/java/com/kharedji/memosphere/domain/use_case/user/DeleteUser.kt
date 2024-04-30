package com.kharedji.memosphere.domain.use_case.user;

import com.kharedji.memosphere.domain.models.user.User
import com.kharedji.memosphere.domain.repository.UserRepository;

public class DeleteUser(
    private val repository: UserRepository
) {

        suspend operator fun invoke() {
            repository.deleteUser()
        }
}
