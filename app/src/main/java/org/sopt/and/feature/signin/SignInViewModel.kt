package org.sopt.and.feature.signin

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import org.sopt.and.api.ServicePool.authService
import org.sopt.and.api.dto.request.RequestSignInDto
import org.sopt.and.api.dto.response.ResponseErrorDto
import org.sopt.and.api.dto.response.ResponseSignInDto
import org.sopt.and.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(context: Context) : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    private fun saveToken(token: String) {
        sharedPreferences?.edit()?.putString("token", token)?.apply()
    }

    fun signIn(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        val request = RequestSignInDto(username = _username.value, password = _password.value)

        authService.signIn(request).enqueue(object : Callback<ResponseSignInDto> {
            override fun onResponse(call: Call<ResponseSignInDto>, response: Response<ResponseSignInDto>) {
                if (response.isSuccessful && response.body()?.result?.token != null) {
                    val token = response.body()!!.result.token
                    saveToken(token)
                    onSuccess(token)
                    context.toast("로그인 성공")
                } else {
                    handleSignInError(context, response)
                    onFailure()
                }
            }

            override fun onFailure(call: Call<ResponseSignInDto>, t: Throwable) {
                context.toast("네트워크 오류: ${t.message}")
                onFailure()
            }
        })
    }

    private fun handleSignInError(context: Context, response: Response<ResponseSignInDto>) {
        val errorBody = response.errorBody()?.string()
        val errorDto = errorBody?.let { Json.decodeFromString<ResponseErrorDto>(it) }

        val message = when (errorDto?.code) {
            "00" -> "Username이나 비밀번호가 틀렸습니다."
            else -> "로그인에 실패하였습니다."
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}