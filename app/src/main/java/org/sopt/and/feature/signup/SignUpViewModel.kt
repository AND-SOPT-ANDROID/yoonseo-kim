package org.sopt.and.feature.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import org.sopt.and.api.ServicePool.authService
import org.sopt.and.api.dto.request.RequestSignUpDto
import org.sopt.and.api.dto.response.ResponseErrorDto
import org.sopt.and.api.dto.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            onFailure("Invalid Input")
            return
        }

        val request = RequestSignUpDto(
            username = username.value,
            password = password.value,
            hobby = hobby.value
        )

        authService.signUp(request).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful && response.body()?.result != null) {
                    onSuccess()
                } else {
                    val errorMessage = handleError(response)
                    onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                onFailure("Network error: ${t.message}")
            }
        })
    }

    private fun handleError(response: Response<ResponseSignUpDto>): String {
        val errorBody = response.errorBody()?.string()
        val errorDto = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it) }

        return when (response.code()) {
            400 -> {
                when (errorDto?.code) {
                    "01" -> "username, 비밀번호, hobby는 8자 이하여야 합니다."
                    else -> "Bad request error."
                }
            }
            404 -> "method와 path 확인이 필요합니다."
            409 -> {
                when (errorDto?.code) {
                    "00" -> "username 중복입니다."
                    else -> ""
                }
            }
            else -> "로그인에 실패하였습니다."
        }
    }
}