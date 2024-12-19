package org.sopt.and.feature.signin.model

import org.sopt.and.core.component.UiEvent
import org.sopt.and.core.component.UiSideEffect
import org.sopt.and.core.component.UiState

class SignInContract {
    data class SignInState(
        val username: String = "",
        val password: String = "",
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SignInEvent : UiEvent {
        data class UpdateUsername(val username: String) : SignInEvent
        data class UpdatePassword(val password: String) : SignInEvent
        data class SignIn(val username: String, val password: String) : SignInEvent
    }

    sealed interface SignInSideEffect : UiSideEffect {
        data class ShowError(val messageId: Int) : SignInSideEffect
        object NavigateToHome : SignInSideEffect
    }
}