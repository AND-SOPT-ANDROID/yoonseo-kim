package org.sopt.and.feature.mypage

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.and.R
import org.sopt.and.core.utils.KeyStorage.ERROR_CODE_00
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_200
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_401
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_403
import org.sopt.and.core.utils.KeyStorage.STATUS_CODE_404
import org.sopt.and.core.utils.KeyStorage.TOKEN
import org.sopt.and.data.dto.response.ResponseErrorDto
import org.sopt.and.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _hobby = MutableStateFlow("")
    val hobby: StateFlow<String> get() = _hobby

    fun initHobby(token: String) {
        val token = sharedPreferences.getString(TOKEN, "").orEmpty()
        if (token.isNotEmpty()) {
            getHobby(token)
        }
    }

    private fun getHobby(token: String) {
        viewModelScope.launch {
            authRepository.getHobby(token)
                .onSuccess { hobby ->
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