package org.sopt.and.feature.signup.model

import org.sopt.and.core.component.UiEvent
import org.sopt.and.core.component.UiSideEffect
import org.sopt.and.core.component.UiState

class SignUpContract {
    data class SignUpState(
        val username: String = "",
        val password: String = "",
        val hobby: String = "",
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SignUpEvent : UiEvent {
        data class UpdateUsername(val username: String) : SignUpEvent
        data class UpdatePassword(val password: String) : SignUpEvent
        data class UpdateHobby(val hobby: String) : SignUpEvent
        data class SignUp(val username: String, val password: String, val hobby: String) : SignUpEvent
    }

    sealed interface SignUpSideEffect : UiSideEffect {
        data class ShowToast(val message: Int) : SignUpSideEffect
        object NavigateToSignIn : SignUpSideEffect
    }
}