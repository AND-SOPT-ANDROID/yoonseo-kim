package org.sopt.and.api.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignUpDto(
    @SerialName("result")
    val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("no")
        val no: Int
    )
}
