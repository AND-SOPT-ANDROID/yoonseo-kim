package org.sopt.and.data.datasource

import org.sopt.and.data.dto.request.RequestSignInDto
import org.sopt.and.data.dto.request.RequestSignUpDto
import org.sopt.and.data.dto.response.BaseResponse
import org.sopt.and.data.dto.response.ResponseHobbyDto
import org.sopt.and.data.dto.response.ResponseSignInDto
import org.sopt.and.data.dto.response.ResponseSignUpDto

interface AuthDataSource {
    suspend fun signUp(request: RequestSignUpDto): BaseResponse<ResponseSignUpDto>

    suspend fun signIn(request: RequestSignInDto): BaseResponse<ResponseSignInDto>

    suspend fun getHobby(request: String): BaseResponse<ResponseHobbyDto>
}