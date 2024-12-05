package org.sopt.and.api.service

import org.sopt.and.api.dto.request.RequestSignInDto
import org.sopt.and.api.dto.request.RequestSignUpDto
import org.sopt.and.api.dto.response.BaseResponse
import org.sopt.and.api.dto.response.ResponseHobbyDto
import org.sopt.and.api.dto.response.ResponseSignInDto
import org.sopt.and.api.dto.response.ResponseSignUpDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/$USER")
    suspend fun signUp(
        @Body request: RequestSignUpDto
    ): BaseResponse<ResponseSignUpDto>

    @POST("/$LOGIN")
    suspend fun signIn(
        @Body request: RequestSignInDto
    ): BaseResponse<ResponseSignInDto>

    @GET("/$USER/$MY_HOBBY")
    suspend fun getHobby(
        @Header("token")
        token: String
    ): BaseResponse<ResponseHobbyDto>

    companion object {
        const val USER = "user"
        const val LOGIN = "login"
        const val MY_HOBBY = "my-hobby"
    }
}