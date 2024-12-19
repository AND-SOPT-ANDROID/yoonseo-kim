package org.sopt.and.core.error

import org.sopt.and.R
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MyPageErrorHandler @Inject constructor() {
    fun getMyPageErrorMessage(throwable: Throwable): Int {
        return when (throwable) {
            is HttpException -> when (throwable.code()) {
                401 -> R.string.error_message_token_blank
                403 -> R.string.error_message_invalid_token
                404 -> R.string.error_message_invalid_url_request
                else -> R.string.error_message_server_error
            }
            is IOException -> R.string.error_message_network_error
            else -> R.string.error_message_unexpected_error
        }
    }
}