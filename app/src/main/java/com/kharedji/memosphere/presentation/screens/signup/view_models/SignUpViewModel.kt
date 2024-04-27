package com.kharedji.memosphere.presentation.screens.signup.view_models

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.AuthResult
import com.kharedji.memosphere.data.utils.State
import com.kharedji.memosphere.domain.models.user.User
import com.kharedji.memosphere.domain.repository.UserRepository
import com.kharedji.memosphere.domain.use_case.user.UserUseCases
import com.kharedji.memosphere.navigation.Screen
import com.kharedji.memosphere.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(State<AuthResult>())
    val uiState: StateFlow<State<AuthResult>> = _uiState
    private val _userState = MutableStateFlow(State<Boolean>())
    val userState: StateFlow<State<Boolean>> = _userState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            repository.signUpUser(email, password).onEach { state ->
                _uiState.emit(state)
            }.launchIn(viewModelScope)
        }
    }

    fun addUserToFirestore(user: User, navController: NavController?, context: Context) {
        viewModelScope.launch {
            userUseCases.addUserToFirestore(user) {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        viewModelScope.launch {
                            userUseCases.addUser(user)
                        }
                        navController?.apply {
                            navigate(Screen.Main.route).apply {
                            }
                        }
                        resetUiState()
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun resetUiState() {
        viewModelScope.launch {
            _uiState.emit(State())
        }
    }
}