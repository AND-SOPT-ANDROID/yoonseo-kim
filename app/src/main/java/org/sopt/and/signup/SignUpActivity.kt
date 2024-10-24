package org.sopt.and.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R
import org.sopt.and.component.SignUpTopBar
import org.sopt.and.component.WavveCustomTextField
import org.sopt.and.ui.theme.ANDANDROIDTheme
import org.sopt.and.utils.KeyStorage.EMAIL
import org.sopt.and.utils.KeyStorage.EMAIL_PATTERN
import org.sopt.and.utils.KeyStorage.PASSWORD
import org.sopt.and.utils.KeyStorage.PASSWORD_MAX_LENGTH
import org.sopt.and.utils.KeyStorage.PASSWORD_MIN_LENGTH
import org.sopt.and.utils.KeyStorage.PASSWORD_PATTERN
import org.sopt.and.utils.toast
import java.util.regex.Pattern

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignUp(
                        modifier = Modifier.padding(innerPadding),
                        onSignUpSuccess = { email, password ->
                            handleSignUp(email, password)
                        }
                    )
                }
            }
        }
    }

    private fun handleSignUp(email: String, password: String) {
        val intent = Intent().apply {
            putExtra(EMAIL, email)
            putExtra(PASSWORD, password)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    onSignUpSuccess: (email: String, password: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_gray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        SignUpTopBar(stringResource(R.string.sign_up_top_bar))

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.sign_up_title),
            fontSize = 20.sp,
            color = Color.White,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        WavveCustomTextField(value = email, onValueChange = { email = it }, hint = stringResource(R.string.sign_up_email_hint))

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.sign_up_email_description),
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_default_gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.height(40.dp))

        WavveCustomTextField(value = password, onValueChange = { password = it }, hint = stringResource(R.string.sign_up_password_hint), isPasswordField = true)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.sign_up_password_description),
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_default_gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.sign_up_social_service),
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_default_gray),
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
            text = stringResource(R.string.sign_up_sns_description),
            fontSize = 12.sp,
            color = colorResource(id = R.color.text_default_gray),
            modifier = Modifier
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (isValidEmail(email) && isValidPassword(password)) {
                    onSignUpSuccess(email, password)
                    context.toast(context.getString(R.string.sign_up_success))
                } else {
                    context.toast(context.getString(R.string.sign_up_failure))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(colorResource(id = R.color.sign_up_button_gray)),
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.sign_up_button_gray)
            )
        ) {
            Text(stringResource(R.string.sign_up_button))
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = EMAIL_PATTERN
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
            PASSWORD_PATTERN.contains(char) -> hasSpecialChar = true
        }

        if (listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3) {
            break
        }
    }

    return password.length in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH && listOf(hasLowercase, hasUppercase, hasDigit, hasSpecialChar).count { it } >= 3
}



@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    ANDANDROIDTheme {
        SignUp(
            onSignUpSuccess = { _, _ -> }
        )
    }
}