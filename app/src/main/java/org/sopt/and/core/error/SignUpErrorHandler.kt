package org.sopt.and.core.error

import org.sopt.and.R
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpErrorHandler @Inject constructor() {
    fun getSignUpErrorMessage(throwable: Throwable): Int {
        return when (throwable) {
            is HttpException -> when (throwable.code()) {
                400 -> R.string.error_message_invalid_request_body
                404 -> R.string.error_message_invalid_url_request
                409 -> R.string.error_message_duplicate_username
                else -> R.string.error_message_server_error
            }
            is IOException -> R.string.error_message_network_error
            else -> R.string.error_message_unexpected_error
        }
    }
}