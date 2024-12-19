package org.sopt.and

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.and.feature.nav.BottomNavigation
import org.sopt.and.feature.signin.SignInScreen
import org.sopt.and.feature.signup.SignUpScreen
import org.sopt.and.ui.theme.ANDANDROIDTheme
import org.sopt.and.core.utils.KeyStorage.AUTH_PREFS
import org.sopt.and.core.utils.KeyStorage.HOME
import org.sopt.and.core.utils.KeyStorage.SIGN_IN
import org.sopt.and.core.utils.KeyStorage.SIGN_UP

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                val navController = rememberNavController()
                val sharedPreferences = getSharedPreferences(AUTH_PREFS, MODE_PRIVATE)
                MainScreen(navController, sharedPreferences)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, sharedPreferences: SharedPreferences) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        NavHost(navController = navController, startDestination = SIGN_IN) {
            composable(SIGN_IN) {
                SignInScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    sharedPreferences = sharedPreferences,
                    onSignUpClick = {
                        navController.navigate(SIGN_UP)
                    },
                    onSignInSuccess = {
                        navController.navigate(HOME) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                )
            }
            composable(SIGN_UP) {
                SignUpScreen(
                    navController,
                    onSignUpSuccess = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                )
            }
            composable(HOME) {
                BottomNavigation(
                    modifier = Modifier
                )
            }
        }
    }
}