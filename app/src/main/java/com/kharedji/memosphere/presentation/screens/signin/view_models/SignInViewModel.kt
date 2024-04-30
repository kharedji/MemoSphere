package com.kharedji.memosphere.presentation.screens.signin.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.kharedji.memosphere.data.utils.State
import com.kharedji.memosphere.domain.repository.UserRepository
import com.kharedji.memosphere.presentation.utils.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(State<AuthResult>())
    val uiState: StateFlow<State<AuthResult>> = _uiState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            repository.signInUser(email, password).onEach { state ->
                _uiState.value = state
            }.launchIn(viewModelScope)
        }
    }

    fun getUserFromFirestore(userUid: String) {
        repository.firestore.collection("users").document(userUid)
            .get()
            .addOnSuccessListener { document ->
                User.user.value = document.toObject(com.kharedji.memosphere.domain.models.user.User::class.java) ?: User.user.value
                viewModelScope.launch{
                    repository.addUserInfo(User.user.value)
                }
            }
            .addOnFailureListener { exception ->
                // Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun resetUiState() {
        viewModelScope.launch {
            _uiState.emit(State())
        }
    }
}