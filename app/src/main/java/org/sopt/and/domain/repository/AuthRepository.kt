package org.sopt.and.domain.repository

import org.sopt.and.domain.entity.SignUpModel

interface AuthRepository {
    suspend fun signUp(request: SignUpModel): Result<Int>
}