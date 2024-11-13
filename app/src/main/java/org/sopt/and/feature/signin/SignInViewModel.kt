package org.sopt.and.feature.signin

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun signIn(
        registeredUsername: String?,
        registeredPassword: String?,
        snackbarHostState: SnackbarHostState,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        if (!registeredUsername.isNullOrBlank() && !registeredPassword.isNullOrBlank() && username.value == registeredUsername && password.value == registeredPassword) {
            viewModelScope.launch {
                onSuccess(username.value)
            }
        } else {
            viewModelScope.launch {
                onFailure()
            }
        }
    }
}