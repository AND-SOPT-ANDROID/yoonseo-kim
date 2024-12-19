package org.sopt.and.data.mapper

import org.sopt.and.data.dto.request.RequestSignInDto
import org.sopt.and.domain.entity.SignInModel

fun SignInModel.toSignInRequest(): RequestSignInDto {
    return RequestSignInDto(
        username = this.username,
        password = this.password
    )
}