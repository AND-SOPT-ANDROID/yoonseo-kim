package org.sopt.and.feature.mypage

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
import org.sopt.and.api.dto.response.ResponseErrorDto
import org.sopt.and.utils.KeyStorage.AUTH_PREFS
import org.sopt.and.utils.KeyStorage.ERROR_CODE_00
import org.sopt.and.utils.KeyStorage.STATUS_CODE_200
import org.sopt.and.utils.KeyStorage.STATUS_CODE_401
import org.sopt.and.utils.KeyStorage.STATUS_CODE_403
import org.sopt.and.utils.KeyStorage.STATUS_CODE_404
import retrofit2.HttpException

class MyPageViewModel : ViewModel() {

    private val _hobby = MutableStateFlow("")
    val hobby: StateFlow<String> get() = _hobby

    fun initHobby(token: String) {
        if (token.isNotEmpty()) {
            getHobby(token)
        }
    }

    private fun getHobby(token: String) {
        viewModelScope.launch {
            runCatching {
                authService.getHobby(token)
            }.onSuccess { response ->
                val hobby = response.result.hobby
                if (hobby.isNotEmpty()) {
                    _hobby.value = hobby
                } else {
                    handleError(STATUS_CODE_200, ERROR_CODE_00)
                }
            }.onFailure { throwable ->
                when (throwable) {
                    is HttpException -> {
                        val errorBody = throwable.response()?.errorBody()?.string()
                        val errorDto = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it) }
                        val statusCode = throwable.code()
                        val errorCode = errorDto?.code.orEmpty()
                        handleError(statusCode, errorCode)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun handleError(statusCode: Int, errorCode: String) {
        val message = when (statusCode) {
            STATUS_CODE_401 -> R.string.error_message_token_blank
            STATUS_CODE_403 -> R.string.error_message_invalid_token
            STATUS_CODE_404 -> R.string.error_message_invalid_url_request
            else -> when (errorCode) {
                ERROR_CODE_00 -> R.string.error_message_error_loading_hobby
                else -> R.string.error_message_unknown_error
            }
        }
    }
}