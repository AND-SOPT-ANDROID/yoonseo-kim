package org.sopt.and.feature.signin

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    var email = mutableStateOf("")
    var password = mutableStateOf("")

    fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        password.value = newPassword
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
                snackbarHostState.showSnackbar("로그인 성공")
                onSuccess(email.value)
            }
        } else {
            viewModelScope.launch {
                snackbarHostState.showSnackbar("로그인 실패")
                onFailure()
            }
        }
    }
}