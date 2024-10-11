package org.sopt.and.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R
import org.sopt.and.component.EmailTextField
import org.sopt.and.component.PasswordTextField
import org.sopt.and.component.SignUpTopBar
import org.sopt.and.signin.SignInActivity
import org.sopt.and.ui.theme.ANDANDROIDTheme
import java.util.regex.Pattern

class SignUpActivity : ComponentActivity() {
    private lateinit var signUpResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signUpResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val email = result.data?.getStringExtra("email")
                val password = result.data?.getStringExtra("password")

                if (email != null && password != null) {
                    finish()
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignUp(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SignUp(name: String, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF1B1B1B)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        SignUpTopBar("회원가입")

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "이메일과 비밀번호만으로\nWavve를 즐길 수 있어요!",
            fontSize = 20.sp,
            color = Color.White,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        EmailTextField(id = email, onValueChange = { email = it }, hint = "wavve@example.com")

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "!! 로그인, 비밀번호 찾기, 알림에 사용되니 정확한 이메일을 입력해 주세요.",
            fontSize = 14.sp,
            color = Color(0xFFA3A3A3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.height(40.dp))

        PasswordTextField(password = password, onValueChange = { password = it }, hint = "Wavve 비밀번호 설정")

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "!! 비밀번호는 8~20자 이내로 영문 대소문자, 숫자, 특수문자 중 3가지 이상 혼용하여 입력해 주세요.",
            fontSize = 14.sp,
            color = Color(0xFFA3A3A3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "또는 다른 서비스로 가입",
            fontSize = 14.sp,
            color = Color(0xFFA3A3A3),
            modifier = Modifier
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_logo_group),
            contentDescription = "logo images",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "- SNS계정으로 간편하게 가입하여 서비스를 이용하실 수 있습니다. 기존 POOQ 계정 또는 Wavve 계정과는 연동되지 않으니 이용에 참고하세요.",
            fontSize = 12.sp,
            color = Color(0xFFA3A3A3),
            modifier = Modifier
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { handleSignUp(context, email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color(0xFF717171)),
            colors = ButtonDefaults.buttonColors(
                Color(0xFF717171)
            )
        ) {
            Text("Wavve 회원가입")
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return Pattern.matches(emailPattern, email)
}

fun isValidPassword(password: String): Boolean {
    var hasLowercase = false
    var hasUppercase = false
    var hasDigit = false
    var hasSpecialChar = false

    for (char in password) {
        when {
            char.isLowerCase() -> hasLowercase = true
            char.isUpperCase() -> hasUppercase = true
            char.isDigit() -> hasDigit = true
            "!@#$%^&*()-_=+[]{}|;:'\",.<>?/".contains(char) -> hasSpecialChar = true
        }

        if (listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3) {
            break
        }
    }

    return password.length in 8..20 && listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3
}

fun handleSignUp(context: Context, email: String, password: String) {
    if (isValidEmail(email) && isValidPassword(password)) {
        val intent = Intent(context, SignInActivity::class.java).apply {
            putExtra("email", email)
            putExtra("password", password)
        }
        context.startActivity(intent)
        Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "회원가입 실패", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    ANDANDROIDTheme {
        SignUp("Android")
    }
}