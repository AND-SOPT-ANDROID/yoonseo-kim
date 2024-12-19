package org.sopt.and.data.mapper

import org.sopt.and.data.dto.request.RequestSignUpDto
import org.sopt.and.domain.entity.SignUpModel

fun SignUpModel.toSignUpRequest(): RequestSignUpDto {
    return RequestSignUpDto(
        username = this.username,
        password = this.password,
        hobby = this.hobby
    )
}