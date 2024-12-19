package org.sopt.and.feature.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.and.core.component.BaseViewModel
import org.sopt.and.core.error.SignUpErrorHandler
import org.sopt.and.domain.entity.SignUpModel
import org.sopt.and.domain.repository.AuthRepository
import org.sopt.and.feature.signup.model.SignUpContract.SignUpEvent
import org.sopt.and.feature.signup.model.SignUpContract.SignUpSideEffect
import org.sopt.and.feature.signup.model.SignUpContract.SignUpState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val errorHandler: SignUpErrorHandler
) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>() {

    override fun createInitialState(): SignUpState = SignUpState()

    override suspend fun handleEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UpdateUsername -> setState { copy(username = event.username) }
            is SignUpEvent.UpdatePassword -> setState { copy(password = event.password) }
            is SignUpEvent.UpdateHobby -> setState { copy(hobby = event.hobby) }
            is SignUpEvent.SignUp -> signUp()
        }
    }

    private fun signUp() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val request = SignUpModel(
                username = uiState.value.username,
                password = uiState.value.password,
                hobby = uiState.value.hobby
            )
            authRepository.signUp(request)
                .onSuccess {
                    setSideEffect { SignUpSideEffect.NavigateToSignIn }
                }.onFailure { throwable ->
                    val errorMessage = errorHandler.getSignUpErrorMessage(throwable)
                    setSideEffect { SignUpSideEffect.ShowToast(errorMessage) }
                }
        }
    }
}