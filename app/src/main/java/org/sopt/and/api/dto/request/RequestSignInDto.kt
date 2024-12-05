package org.sopt.and.api.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignInDto(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)
