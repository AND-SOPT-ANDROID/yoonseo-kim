package org.sopt.and.feature.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.sopt.and.R
import org.sopt.and.component.SignUpTopBar
import org.sopt.and.component.SocialLoginItem
import org.sopt.and.component.WavveCustomTextField
import org.sopt.and.utils.toast

@Composable
fun SignUpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onSignUpSuccess: (email: String, password: String) -> Unit,
    viewModel: SignUpViewModel = viewModel(),
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val hobby by viewModel.hobby.collectAsStateWithLifecycle()

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

        WavveCustomTextField(
            value = username,
            onValueChange = { viewModel.updateUsername(it) },
            hint = stringResource(R.string.sign_up_username_hint)
        )

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

        WavveCustomTextField(
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            hint = stringResource(R.string.sign_up_password_hint),
            isPasswordField = true
        )

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

        WavveCustomTextField(
            value = hobby,
            onValueChange = { viewModel.updateHobby(it) },
            hint = stringResource(R.string.sign_up_hobby_hint)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.sign_up_hobby_description),
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_default_gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
        )

        Spacer(modifier = Modifier.height(40.dp))

        SocialLoginItem(
            textResId = R.string.sign_up_social_service,
            imageResId = R.drawable.ic_logo_group
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
                viewModel.signUp(
                    onSuccess = { email, password ->
                        onSignUpSuccess(email, password)
                        context.toast(context.getString(R.string.sign_up_success))
                    },
                    onFailure = {
                        context.toast(context.getString(R.string.sign_up_failure))
                    }
                )
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