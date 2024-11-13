package org.sopt.and.api.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignInDto(
    @SerialName("result")
    val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("token")
        val token: String
    )
}
