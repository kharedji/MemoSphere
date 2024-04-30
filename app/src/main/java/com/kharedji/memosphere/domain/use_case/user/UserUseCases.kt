package com.kharedji.memosphere.domain.use_case.user

data class UserUseCases (
    val addUser: AddUser,
    val addUserToFirestore: AddUserToFirestore,
    val getUser: GetUser,
    val addAvatarToFirebase: AddAvatarToFirebase,
    val deleteUser: DeleteUser
)