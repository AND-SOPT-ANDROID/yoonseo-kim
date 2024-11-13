package org.sopt.and

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.sopt.and.feature.nav.BottomNavigation
import org.sopt.and.feature.signin.SignInScreen
import org.sopt.and.feature.signup.SignUpScreen
import org.sopt.and.model.BottomNavItem
import org.sopt.and.ui.theme.ANDANDROIDTheme
import org.sopt.and.utils.KeyStorage.HOME
import org.sopt.and.utils.KeyStorage.SIGN_IN
import org.sopt.and.utils.KeyStorage.SIGN_UP

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    var registeredHobby by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        NavHost(navController = navController, startDestination = SIGN_IN) {
            composable(SIGN_IN) {
                SignInScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState,
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
                    registeredHobby,
                    modifier = Modifier
                )
            }
        }
    }
}