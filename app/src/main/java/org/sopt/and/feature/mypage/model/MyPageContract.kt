package org.sopt.and.feature.mypage.model

import org.sopt.and.core.component.UiEvent
import org.sopt.and.core.component.UiSideEffect
import org.sopt.and.core.component.UiState

class MyPageContract {
    data class MyPageState(
        val hobby: String = "",
        val isLoading: Boolean = false
    ) : UiState

    sealed interface MyPageEvent : UiEvent {
        data class LoadUserHobby(val token: String) : MyPageEvent
    }

    sealed interface MyPageSideEffect : UiSideEffect {
        data class ShowError(val messageId: Int) : MyPageSideEffect
    }
}