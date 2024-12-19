package org.sopt.and.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.and.R
import org.sopt.and.data.dto.response.ResponseErrorDto
import org.sopt.and.core.utils.KeyStorage.ERROR_CODE_00
import org.sopt.and.core.utils.KeyStorage.ERROR_CODE_01
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_400
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_404
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_409
import org.sopt.and.core.utils.KeyStorage.TEXTFIELD_MAX_LENGTH
import org.sopt.and.domain.entity.SignUpModel
import org.sopt.and.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _hobby = MutableStateFlow("")
    val hobby: StateFlow<String> = _hobby

    fun updateUsername(newUsername: String) {
        if (newUsername.length <= TEXTFIELD_MAX_LENGTH) {
            _username.value = newUsername
        }
    }

    fun updatePassword(newPassword: String) {
        if (newPassword.length <= TEXTFIELD_MAX_LENGTH) {
            _password.value = newPassword
        }
    }

    fun updateHobby(newHobby: String) {
        if (newHobby.length <= TEXTFIELD_MAX_LENGTH) {
            _hobby.value = newHobby
        }
    }

    private fun isValidUser(): Boolean {
        return username.value.length <= TEXTFIELD_MAX_LENGTH &&
                password.value.length <= TEXTFIELD_MAX_LENGTH &&
                hobby.value.length <= TEXTFIELD_MAX_LENGTH
    }

    fun signUp(onSuccess: () -> Unit, onFailure: (Int) -> Unit) {
        if (!isValidUser()) {
            onFailure(R.string.error_message_invalid_user_info)
            return
        }

        val request = SignUpModel(
            username = username.value,
            password = password.value,
            hobby = hobby.value
        )

        viewModelScope.launch {
            runCatching {
                authRepository.signUp(request)
            }.onSuccess {
                onSuccess()
            }.onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> handleSignUpError(throwable)
                    is IOException -> R.string.error_message_network_error
                    else -> R.string.error_message_unexpected_error
                }
                onFailure(errorMessage)
            }
        }
    }

    private fun handleSignUpError(exception: HttpException): Int {
        val errorBody = exception.response()?.errorBody()?.string()
        val errorCode = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it).code }
        return when (exception.code()) {
            STATUS_CODE_400 -> when (errorCode) {
                ERROR_CODE_00 -> R.string.error_message_invalid_request_body
                ERROR_CODE_01 -> R.string.error_message_under_8_letters
                else -> R.string.error_message_wrong_request
            }
            STATUS_CODE_404 -> R.string.error_message_invalid_url_request
            STATUS_CODE_409 -> R.string.error_message_duplicate_username
            else -> R.string.error_message_server_error
        }
    }
}