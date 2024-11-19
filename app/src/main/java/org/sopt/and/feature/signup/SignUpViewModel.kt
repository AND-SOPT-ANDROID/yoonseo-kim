package org.sopt.and.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.and.api.ServicePool.authService
import org.sopt.and.api.dto.request.RequestSignUpDto
import org.sopt.and.api.dto.response.ResponseErrorDto
import retrofit2.HttpException
import java.io.IOException

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

    private fun isValidUser(): Boolean {
        return username.value.length <= 8 &&
                password.value.length <= 8 &&
                hobby.value.length <= 8
    }

    fun signUp(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (!isValidUser()) {
            onFailure("유효하지 않은 사용자 정보입니다.")
            return
        }

        val request = RequestSignUpDto(
            username = username.value,
            password = password.value,
            hobby = hobby.value
        )

        viewModelScope.launch {
            runCatching {
                authService.signUp(request)
            }.onSuccess { response ->
                onSuccess()
            }.onFailure { throwable ->
                val errorMessage = when (throwable) {
                    is HttpException -> handleSignUpError(throwable)
                    is IOException -> "네트워크 오류: ${throwable.message}"
                    else -> "unexpected error"
                }
                onFailure(errorMessage)
            }
        }
    }

    private fun handleSignUpError(exception: HttpException): String {
        val errorBody = exception.response()?.errorBody()?.string()
        val errorCode = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it).code }
        return when (exception.code()) {
            400 -> when (errorCode) {
                "00" -> "request body가 유효하지 않습니다."
                "01" -> "username, password, hobby는 8자 이하여야 합니다."
                else -> "잘못된 요청입니다."
            }
            404 -> "유효하지 않은 경로 요청입니다."
            409 -> "중복된 username입니다."
            else -> "서버 오류 발생 (${exception.code()})"
        }
    }
}