package org.sopt.and.data.repositoryimpl

import org.sopt.and.data.datasource.AuthDataSource
import org.sopt.and.data.mapper.toSignInRequest
import org.sopt.and.data.mapper.toSignUpRequest
import org.sopt.and.domain.entity.SignInModel
import org.sopt.and.domain.entity.SignUpModel
import org.sopt.and.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signUp(request: SignUpModel): Result<Int> =
        runCatching {
            val response = authDataSource.signUp(request.toSignUpRequest())
            response.result.userNumber
        }

    override suspend fun signIn(request: SignInModel): Result<String> =
        runCatching {
            val response = authDataSource.signIn(request.toSignInRequest())
            response.result.token
        }

    override suspend fun getHobby(request: String): Result<String> =
        runCatching {
            val response = authDataSource.getHobby(request)
            response.result.hobby
        }
}