package org.sopt.and.feature.mypage

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.and.R
import org.sopt.and.core.component.BaseViewModel
import org.sopt.and.core.error.MyPageErrorHandler
import org.sopt.and.core.utils.KeyStorage.TOKEN
import org.sopt.and.domain.repository.AuthRepository
import org.sopt.and.feature.mypage.model.MyPageContract.MyPageEvent
import org.sopt.and.feature.mypage.model.MyPageContract.MyPageSideEffect
import org.sopt.and.feature.mypage.model.MyPageContract.MyPageState
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences,
    private val errorHandler: MyPageErrorHandler
) : BaseViewModel<MyPageState, MyPageSideEffect, MyPageEvent>() {

    override fun createInitialState(): MyPageState = MyPageState()

    override suspend fun handleEvent(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.LoadUserHobby -> {
                val token = sharedPreferences.getString(TOKEN, "").orEmpty()
                if (token.isNotEmpty()) {
                    getHobby(token)
                } else {
                    setSideEffect { MyPageSideEffect.ShowError(R.string.error_message_token_blank) }
                }
            }
        }
    }

    private fun getHobby(token: String) {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.getHobby(token)
                .onSuccess { hobby ->
                    setState { copy(hobby = hobby, isLoading = false) }
                }.onFailure { throwable ->
                    val errorMessage = errorHandler.getMyPageErrorMessage(throwable)
                    setSideEffect { MyPageSideEffect.ShowError(errorMessage) }
                    setState { copy(isLoading = false) }
                }
        }
    }
}