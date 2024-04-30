package com.kharedji.memosphere.presentation.screens.profile.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharedji.memosphere.domain.repository.UserRepository
import com.kharedji.memosphere.domain.use_case.user.UserUseCases
import com.kharedji.memosphere.presentation.utils.User
import com.kharedji.memosphere.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    fun uploadAvatar(uri: Uri){
        userUseCases.addAvatarToFirebase(uri) {
            when(it) {
                is Resource.Loading -> {
                    _uiState.value = ProfileState(isLoading = true)
                }
                is Resource.Success -> {
                    _uiState.value = ProfileState(isLoading = false, avatarUri = it.data ?: "")
                    User.user.value = User.user.value.copy(avatarUrl = it.data ?: "")
                    viewModelScope.launch {
                        userUseCases.addUser(User.user.value)
                        userUseCases.addUserToFirestore(User.user.value){
                            
                        }
                    }
                }
                is Resource.Error -> {
                    _uiState.value = ProfileState(isLoading = false, error = it.message ?: "An error occurred")
                }
            }
        }
    }
}