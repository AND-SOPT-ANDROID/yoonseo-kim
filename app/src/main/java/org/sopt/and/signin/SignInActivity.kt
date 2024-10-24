package org.sopt.and.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import org.sopt.and.R
import org.sopt.and.component.SignInTopBar
import org.sopt.and.component.WavveCustomTextField
import org.sopt.and.mypage.MyActivity
import org.sopt.and.signup.SignUpActivity
import org.sopt.and.ui.theme.ANDANDROIDTheme
import org.sopt.and.utils.KeyStorage.EMAIL
import org.sopt.and.utils.KeyStorage.PASSWORD

class SignInActivity : ComponentActivity() {

    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>
    private var registeredEmail by mutableStateOf("")
    private var registeredPassword by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                registeredEmail = result.data?.getStringExtra(EMAIL).orEmpty()
                registeredPassword = result.data?.getStringExtra(PASSWORD).orEmpty()
            }
        }

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
                        onSignUpClick = { navigateToSignUp() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        signInResultLauncher.launch(intent)
    }
}

@Composable
fun SignIn(
    registeredEmail: String?,
    registeredPassword: String?,
    snackbarHostState: SnackbarHostState,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        SignInTopBar(stringResource(R.string.sign_in_top_bar))

        Spacer(modifier = Modifier.height(50.dp))

        WavveCustomTextField(value = email, onValueChange = { email = it }, hint = stringResource(R.string.sign_in_email_hint))

        Spacer(modifier = Modifier.height(10.dp))

        WavveCustomTextField(value = password, onValueChange = { password = it }, hint = stringResource(R.string.sign_in_password_hint), isPasswordField = true)

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (!registeredEmail.isNullOrBlank() && !registeredPassword.isNullOrBlank() && email == registeredEmail && password == registeredPassword) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.sign_in_success))
                        delay(1)
                        Intent(context, MyActivity::class.java).apply {
                            putExtra(EMAIL, email)
                            context.startActivity(this)
                        }
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.sign_in_failure))
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                colorResource(R.color.sign_in_button_blue)
            )
        ) {
            Text(stringResource(R.string.sign_in_button))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.sign_in_to_find_id_button),
                fontSize = 12.sp,
                color = colorResource(R.color.text_default_gray),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = stringResource(R.string.sign_in_slash),
                fontSize = 12.sp,
                color = colorResource(R.color.text_default_gray),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = stringResource(R.string.sign_in_to_reset_password_button),
                fontSize = 12.sp,
                color = colorResource(R.color.text_default_gray),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = stringResource(R.string.sign_in_slash),
                fontSize = 12.sp,
                color = colorResource(R.color.text_default_gray),
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            )

            Text(
                text = stringResource(R.string.sign_in_to_sign_up_button),
                fontSize = 12.sp,
                color = colorResource(R.color.text_default_gray),
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable {
                        onSignUpClick()
                    },
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.sign_in_social_service),
            fontSize = 14.sp,
            color = colorResource(R.color.text_default_gray),
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
            text = stringResource(R.string.sign_in_sns_description),
            fontSize = 12.sp,
            color = colorResource(R.color.text_default_gray),
            modifier = Modifier
                .padding(horizontal = 15.dp),
        )
    }
}


