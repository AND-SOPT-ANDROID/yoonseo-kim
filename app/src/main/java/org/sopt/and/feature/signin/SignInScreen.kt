package org.sopt.and.feature.signin

import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.sopt.and.R
import org.sopt.and.component.SignInTopBar
import org.sopt.and.component.SocialLoginItem
import org.sopt.and.component.WavveCustomTextField
import org.sopt.and.utils.noRippleClickable
import org.sopt.and.utils.toast

@Composable
fun SignInScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    onSignUpClick: () -> Unit,
    onSignInSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel = remember { SignInViewModel(context) }
    val username by viewModel.username.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_gray))
                .imePadding()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            SignInTopBar()

            Spacer(modifier = Modifier.height(50.dp))

            WavveCustomTextField(
                value = username,
                onValueChange = viewModel::onUsernameChanged,
                hint = stringResource(R.string.sign_in_username_hint)
            )

            Spacer(modifier = Modifier.height(10.dp))

            WavveCustomTextField(
                value = password,
                onValueChange = viewModel::onPasswordChanged,
                hint = stringResource(R.string.sign_in_password_hint),
                isPasswordField = true
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.signIn(
                        onSuccess = { token ->
                            onSignInSuccess()
                            context.toast(context.getString(R.string.sign_in_success))
                        },
                        onFailure = { errorMessageId ->
                            context.toast(context.getString(errorMessageId))
                        }
                    )
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
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                Text(
                    text = stringResource(R.string.sign_in_slash),
                    fontSize = 12.sp,
                    color = colorResource(R.color.text_default_gray),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                Text(
                    text = stringResource(R.string.sign_in_to_reset_password_button),
                    fontSize = 12.sp,
                    color = colorResource(R.color.text_default_gray),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                Text(
                    text = stringResource(R.string.sign_in_slash),
                    fontSize = 12.sp,
                    color = colorResource(R.color.text_default_gray),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                Text(
                    text = stringResource(R.string.sign_in_to_sign_up_button),
                    fontSize = 12.sp,
                    color = colorResource(R.color.text_default_gray),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .noRippleClickable { onSignUpClick() }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            SocialLoginItem(
                textResId = R.string.sign_in_social_service,
                imageResId = R.drawable.ic_logo_group
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.sign_in_sns_description),
                fontSize = 12.sp,
                color = colorResource(R.color.text_default_gray),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}