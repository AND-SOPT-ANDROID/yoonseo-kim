package org.sopt.and.feature.mypage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import org.sopt.and.api.ServicePool.authService
import org.sopt.and.api.dto.response.ResponseErrorDto
import org.sopt.and.api.dto.response.ResponseHobbyDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageViewModel(context: Context) : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _hobby = MutableStateFlow("")
    val hobby: StateFlow<String> get() = _hobby

    fun initHobby(token: String) {
        Log.e("MyPageViewModel", "initHobby called with token: $token")
        if (token.isNotEmpty()) {
            getHobby(token)
        }
    }

    private fun getHobby(token: String) {
        authService.getHobby(token).enqueue(object : Callback<ResponseHobbyDto> {
            override fun onResponse(call: Call<ResponseHobbyDto>, response: Response<ResponseHobbyDto>) {
                if (response.isSuccessful) {
                    val hobby = response.body()?.result?.hobby
                    if (!hobby.isNullOrEmpty()) {
                        _hobby.value = hobby
                    } else {
                        handleGetError(response)
                    }
                } else {
                    handleGetError(response)
                }
            }

            override fun onFailure(call: Call<ResponseHobbyDto>, t: Throwable) {

            }
        })
    }

    private fun handleGetError(response: Response<ResponseHobbyDto>) {
        val errorBody = response.errorBody()?.string()
        val errorDto = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it) }

        val message = when (errorDto?.code) {
            "00" -> "취미 정보를 불러올 수 없습니다."
            else -> "알 수 없는 오류가 발생했습니다."
        }
    }
}