package org.sopt.and.feature.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _hobby = MutableStateFlow("")
    val hobby: StateFlow<String> = _hobby

    fun updateUsername(newUsername: String) {
        if (newUsername.length <= 8) {
            _username.value = newUsername
        }
    }

    fun updatePassword(newPassword: String) {
        if (newPassword.length <= 8) {
            _password.value = newPassword
        }
    }

    fun updateHobby(newHobby: String) {
        if (newHobby.length <= 8) {
            _hobby.value = newHobby
        }
    }

//    private fun isValidUsername(): Boolean {
//        val emailPattern = EMAIL_PATTERN
//        return Pattern.matches(emailPattern, username.value)
//    }
//
//    private fun isValidPassword(): Boolean {
//        var hasLowercase = false
//        var hasUppercase = false
//        var hasDigit = false
//        var hasSpecialChar = false
//
//        for (char in password.value) {
//            when {
//                char.isLowerCase() -> hasLowercase = true
//                char.isUpperCase() -> hasUppercase = true
//                char.isDigit() -> hasDigit = true
//                PASSWORD_PATTERN.contains(char) -> hasSpecialChar = true
//            }
//
//            if (listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3) {
//                break
//            }
//        }
//
//        return password.value.length <= TEXTFIELD_MAX_LENGTH &&
//                listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3
//    }

    private fun isValidUser(): Boolean {
        return username.value.length <= 8 &&
                password.value.length <= 8 &&
                hobby.value.length <= 8
    }

    fun signUp(onSuccess: (String, String) -> Unit, onFailure: () -> Unit) {
        if (isValidUser()) {
            onSuccess(username.value, password.value)
        } else {
            onFailure()
        }
    }
}