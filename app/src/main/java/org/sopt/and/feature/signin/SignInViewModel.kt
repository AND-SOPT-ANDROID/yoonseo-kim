package org.sopt.and.feature.signin

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.and.core.component.BaseViewModel
import org.sopt.and.core.error.SignInErrorHandler
import org.sopt.and.core.utils.KeyStorage.TOKEN
import org.sopt.and.domain.entity.SignInModel
import org.sopt.and.domain.repository.AuthRepository
import org.sopt.and.feature.signin.model.SignInContract.SignInEvent
import org.sopt.and.feature.signin.model.SignInContract.SignInSideEffect
import org.sopt.and.feature.signin.model.SignInContract.SignInState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val authRepository: AuthRepository,
    private val errorHandler: SignInErrorHandler
) : BaseViewModel<SignInState, SignInSideEffect, SignInEvent>() {

    override fun createInitialState(): SignInState = SignInState()

    override suspend fun handleEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UpdateUsername -> setState { copy(username = event.username) }
            is SignInEvent.UpdatePassword -> setState { copy(password = event.password) }
            is SignInEvent.SignIn -> signIn()
        }
    }

    private fun signIn() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val request = SignInModel(uiState.value.username, uiState.value.password)
            authRepository.signIn(request)
                .onSuccess { token ->
                    saveToken(token)
                    setSideEffect { SignInSideEffect.NavigateToHome }
                }
                .onFailure { throwable ->
                    val errorMessage = errorHandler.getSignInErrorMessage(throwable)
                    setSideEffect { SignInSideEffect.ShowError(errorMessage) }
                }
        }
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit()?.putString(TOKEN, token)?.apply()
    }
}