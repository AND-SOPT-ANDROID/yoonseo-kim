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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.sopt.and.R
import org.sopt.and.core.component.SignUpTopBar
import org.sopt.and.core.component.SocialLoginItem
import org.sopt.and.core.component.WavveCustomTextField
import org.sopt.and.core.utils.context.toast
import org.sopt.and.feature.signup.model.SignUpContract.SignUpSideEffect
import org.sopt.and.feature.signup.model.SignUpContract.SignUpEvent

@Composable
fun SignUpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onSignUpSuccess: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SignUpSideEffect.ShowToast -> context.toast(context.getString(effect.message))
                SignUpSideEffect.NavigateToSignIn -> onSignUpSuccess()
            }
        }
    }

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
            value = uiState.username,
            onValueChange = { viewModel.setEvent(SignUpEvent.UpdateUsername(it)) },
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
            value = uiState.password,
            onValueChange = { viewModel.setEvent(SignUpEvent.UpdatePassword(it)) },
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
            value = uiState.hobby,
            onValueChange = { viewModel.setEvent(SignUpEvent.UpdateHobby(it)) },
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
                viewModel.setEvent(SignUpEvent.SignUp(uiState.username, uiState.password, uiState.hobby))
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