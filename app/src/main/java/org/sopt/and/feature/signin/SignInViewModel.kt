package org.sopt.and.feature.signin

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.and.R
import org.sopt.and.data.dto.response.ResponseErrorDto
import org.sopt.and.core.utils.KeyStorage.ERROR_CODE_01
import org.sopt.and.core.utils.KeyStorage.ERROR_CODE_02
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_400
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_403
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_404
import org.sopt.and.core.utils.KeyStorage.TOKEN
import org.sopt.and.domain.entity.SignInModel
import org.sopt.and.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

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
        sharedPreferences.edit()?.putString(TOKEN, token)?.apply()
    }

    fun signIn(
        onSuccess: (String) -> Unit,
        onFailure: (Int) -> Unit
    ) {
        val request = SignInModel(username = _username.value, password = _password.value)

        viewModelScope.launch {
            authRepository.signIn(request)
                .onSuccess { token ->
                    saveToken(token)
                    onSuccess(token)
                }
                .onFailure { throwable ->
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
                    STATUS_CODE_400 -> when (errorCode) {
                        ERROR_CODE_01 -> R.string.error_message_invalid_request_body
                        ERROR_CODE_02 -> R.string.error_message_invalid_password
                        else -> R.string.error_message_wrong_request
                    }
                    STATUS_CODE_403 -> R.string.error_message_wrong_password
                    STATUS_CODE_404 -> R.string.error_message_invalid_url_request
                    else -> R.string.error_message_server_error
                }
            }
            is IOException -> R.string.error_message_network_error
            else -> R.string.error_message_unexpected_error
        }
    }
}