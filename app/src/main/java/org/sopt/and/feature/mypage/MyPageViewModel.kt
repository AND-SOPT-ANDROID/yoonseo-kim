package org.sopt.and.feature.mypage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyPageViewModel : ViewModel() {
    private var registeredEmail = mutableStateOf("")

    fun initUserName(email: String) {
        if (registeredEmail.value.isEmpty()) {
            registeredEmail.value = email
        }
    }
}