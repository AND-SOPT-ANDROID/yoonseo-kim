package org.sopt.and.feature.signin

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun signIn(
        registeredEmail: String?,
        registeredPassword: String?,
        snackbarHostState: SnackbarHostState,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        if (!registeredEmail.isNullOrBlank() && !registeredPassword.isNullOrBlank() && email.value == registeredEmail && password.value == registeredPassword) {
            viewModelScope.launch {
                onSuccess(email.value)
            }
        } else {
            viewModelScope.launch {
                onFailure()
            }
        }
    }
}