package org.sopt.and.api.service

import org.sopt.and.api.dto.request.RequestSignInDto
import org.sopt.and.api.dto.request.RequestSignUpDto
import org.sopt.and.api.dto.response.ResponseHobbyDto
import org.sopt.and.api.dto.response.ResponseSignInDto
import org.sopt.and.api.dto.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/user")
    fun signUp(
        @Body request: RequestSignUpDto
    ): Call<ResponseSignUpDto>

    @POST("/login")
    fun signIn(
        @Body request: RequestSignInDto
    ): Call<ResponseSignInDto>

    @GET("/user/my-hobby")
    fun getHobby(
        @Header("Authorization")
        token: String
    ): Call<ResponseHobbyDto>
}