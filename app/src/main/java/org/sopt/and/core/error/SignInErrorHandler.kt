package org.sopt.and.core.error

import org.sopt.and.R
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignInErrorHandler @Inject constructor() {
    fun getSignInErrorMessage(throwable: Throwable): Int {
        return when (throwable) {
            is HttpException -> when (throwable.code()) {
                400 -> R.string.error_message_invalid_request_body
                403 -> R.string.error_message_wrong_password
                404 -> R.string.error_message_invalid_url_request
                else -> R.string.error_message_server_error
            }
            is IOException -> R.string.error_message_network_error
            else -> R.string.error_message_unexpected_error
        }
    }
}