package org.sopt.and.feature.signin

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.and.R
import org.sopt.and.api.ServicePool.authService
import org.sopt.and.api.dto.request.RequestSignInDto
import org.sopt.and.api.dto.response.ResponseErrorDto
import org.sopt.and.utils.KeyStorage.AUTH_PREFS
import org.sopt.and.utils.KeyStorage.ERROR_CODE_01
import org.sopt.and.utils.KeyStorage.ERROR_CODE_02
import org.sopt.and.utils.KeyStorage.TOKEN
import retrofit2.HttpException
import java.io.IOException

class SignInViewModel(context: Context) : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)

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
        sharedPreferences?.edit()?.putString(TOKEN, token)?.apply()
    }

    fun signIn(
        onSuccess: (String) -> Unit,
        onFailure: (Int) -> Unit
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
                    onFailure(R.string.sign_in_failure)
                }
            }.onFailure { throwable ->
                val errorMessageId = handleSignInError(throwable)
                onFailure(errorMessageId)
            }
        }
    }

    private fun handleSignInError(throwable: Throwable): Int {
        return when (throwable) {
            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                val errorCode = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it).code }
                when (throwable.code()) {
                    400 -> when (errorCode) {
                        ERROR_CODE_01 -> R.string.error_message_invalid_request_body
                        ERROR_CODE_02 -> R.string.error_message_invalid_password
                        else -> R.string.error_message_wrong_request
                    }
                    403 -> R.string.error_message_wrong_password
                    404 -> R.string.error_message_invalid_url_request
                    else -> R.string.error_message_server_error
                }
            }
            is IOException -> R.string.error_message_network_error
            else -> R.string.error_message_unexpected_error
        }
    }
}