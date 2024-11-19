package org.sopt.and.feature.signin

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.and.api.ServicePool.authService
import org.sopt.and.api.dto.request.RequestSignInDto
import org.sopt.and.api.dto.response.ResponseErrorDto
import org.sopt.and.api.dto.response.ResponseSignInDto
import org.sopt.and.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class SignInViewModel(context: Context) : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

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

    private fun saveToken(token: String) {
        sharedPreferences?.edit()?.putString("token", token)?.apply()
    }

    fun signIn(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val request = RequestSignInDto(username = _username.value, password = _password.value)

        viewModelScope.launch {
            runCatching {
                authService.signIn(request)
            }.onSuccess { response ->
                if (response.result.token.isNotEmpty()) {
                    val token = response.result.token
                    saveToken(token)
                    onSuccess(token)
                } else {
                    onFailure("로그인에 실패하였습니다.")
                }
            }.onFailure { throwable ->
                val errorMessage = handleSignInError(throwable)
                onFailure(errorMessage)
            }
        }
    }

    private fun handleSignInError(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                val errorCode = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it).code }
                when (throwable.code()) {
                    400 -> when (errorCode) {
                        "01" -> "request body가 유효하지 않습니다."
                        "02" -> "로그인 요청 정보가 잘못되었습니다. (올바르지 않은 password)"
                        else -> "잘못된 요청입니다."
                    }
                    403 -> "password가 틀렸습니다."
                    404 -> "유효하지 않은 경로 요청입니다."
                    else -> "서버 오류 발생 (${throwable.code()})"
                }
            }
            is IOException -> "네트워크 오류: ${throwable.message}"
            else -> "unexpected error"
        }
    }
}