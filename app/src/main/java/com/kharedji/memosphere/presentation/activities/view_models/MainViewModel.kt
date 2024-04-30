package com.kharedji.memosphere.presentation.activities.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharedji.memosphere.domain.use_case.user.UserUseCases
import com.kharedji.memosphere.presentation.utils.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCase: UserUseCases
): ViewModel() {

    init {
        getUser()
    }
    fun getUser() {
        viewModelScope.launch {
            userUseCase.getUser().collectLatest {
                User.user.value = it
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            userUseCase.deleteUser()
        }
    }
}