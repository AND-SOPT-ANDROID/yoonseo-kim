package org.sopt.and.feature.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.sopt.and.utils.KeyStorage.EMAIL_PATTERN
import org.sopt.and.utils.KeyStorage.PASSWORD_MAX_LENGTH
import org.sopt.and.utils.KeyStorage.PASSWORD_MIN_LENGTH
import org.sopt.and.utils.KeyStorage.PASSWORD_PATTERN
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    private fun isValidEmail(): Boolean {
        val emailPattern = EMAIL_PATTERN
        return Pattern.matches(emailPattern, email.value)
    }

    private fun isValidPassword(): Boolean {
        var hasLowercase = false
        var hasUppercase = false
        var hasDigit = false
        var hasSpecialChar = false

        for (char in password.value) {
            when {
                char.isLowerCase() -> hasLowercase = true
                char.isUpperCase() -> hasUppercase = true
                char.isDigit() -> hasDigit = true
                PASSWORD_PATTERN.contains(char) -> hasSpecialChar = true
            }

            if (listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3) {
                break
            }
        }

        return password.value.length in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH &&
                listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3
    }

    fun signUp(onSuccess: (String, String) -> Unit, onFailure: () -> Unit) {
        if (isValidEmail() && isValidPassword()) {
            onSuccess(email.value, password.value)
        } else {
            onFailure()
        }
    }
}