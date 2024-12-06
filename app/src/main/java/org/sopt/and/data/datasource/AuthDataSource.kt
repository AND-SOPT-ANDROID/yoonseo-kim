package org.sopt.and.data.datasource

import org.sopt.and.data.dto.request.RequestSignUpDto
import org.sopt.and.data.dto.response.BaseResponse
import org.sopt.and.data.dto.response.ResponseSignUpDto

interface AuthDataSource {
    suspend fun signUp(request: RequestSignUpDto): BaseResponse<ResponseSignUpDto>
}