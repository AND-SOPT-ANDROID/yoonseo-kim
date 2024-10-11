package org.sopt.and.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import org.sopt.and.R
import org.sopt.and.component.EmailTextField
import org.sopt.and.component.PasswordTextField
import org.sopt.and.component.SignInTopBar
import org.sopt.and.mypage.MyActivity
import org.sopt.and.signup.SignUpActivity
import org.sopt.and.ui.theme.ANDANDROIDTheme

class SignInActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val registeredEmail = intent.getStringExtra("email")
        val registeredPassword = intent.getStringExtra("password")

        setContent {
            ANDANDROIDTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    SignIn(
                        registeredEmail = registeredEmail,
                        registeredPassword = registeredPassword,
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SignIn(
    registeredEmail: String?,
    registeredPassword: String?,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF1B1B1B))
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        SignInTopBar("Wavve")

        Spacer(modifier = Modifier.height(50.dp))

        EmailTextField(id = email, onValueChange = { email = it }, hint = "이메일 주소 또는 아이디")

        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextField(password = password, onValueChange = { password = it }, hint = "비밀번호")

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (!registeredEmail.isNullOrBlank() && !registeredPassword.isNullOrBlank() && email == registeredEmail && password == registeredPassword) {
                    scope.launch {
                        snackbarHostState.showSnackbar("로그인 성공")
                        delay(1)
                        Intent(context, MyActivity::class.java).apply {
                            putExtra("email", email)
                            context.startActivity(this)
                        }
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("로그인 실패")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                Color(0xFF1253FA)
            )
        ) {
            Text("로그인")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "아이디 찾기",
                fontSize = 12.sp,
                color = Color(0xFFA3A3A3),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = "|",
                fontSize = 12.sp,
                color = Color(0xFFA3A3A3),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = "비밀번호 재설정",
                fontSize = 12.sp,
                color = Color(0xFFA3A3A3),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = "|",
                fontSize = 12.sp,
                color = Color(0xFFA3A3A3),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = "회원가입",
                fontSize = 12.sp,
                color = Color(0xFFA3A3A3),
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable {
                        navigateToSignUp(context)
                    },
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "또는 다른 서비스 계정으로 로그인",
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
            text = "- SNS계정으로 간편하게 가입하여 서비스를 이용하실 수 있습니다.\n 기존 POOQ 계정 또는 Wavve 계정과는 연동되지 않으니 이용에 참고하세요.",
            fontSize = 12.sp,
            color = Color(0xFFA3A3A3),
            modifier = Modifier
                .padding(horizontal = 15.dp),
        )
    }
}

fun navigateToSignUp(context: Context) {
    val intent = Intent(context, SignUpActivity::class.java)
    context.startActivity(intent)
}

//@Preview(showBackground = true)
//@Composable
//fun SignInPreview() {
//    ANDANDROIDTheme {
////        SignIn("android@gmail.com", "andsopt", "", "")
//    }
//}