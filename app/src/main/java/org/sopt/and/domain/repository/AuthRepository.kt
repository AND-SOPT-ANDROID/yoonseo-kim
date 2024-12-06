package org.sopt.and.domain.repository

import org.sopt.and.domain.entity.SignInModel
import org.sopt.and.domain.entity.SignUpModel

interface AuthRepository {
    suspend fun signUp(request: SignUpModel): Result<Int>

    suspend fun signIn(request: SignInModel): Result<String>

    suspend fun getHobby(request: String): Result<String>
}