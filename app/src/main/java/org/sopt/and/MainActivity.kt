package org.sopt.and

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.and.feature.nav.BottomNavigation
import org.sopt.and.feature.signin.SignInScreen
import org.sopt.and.feature.signup.SignUpScreen
import org.sopt.and.model.BottomNavItem
import org.sopt.and.ui.theme.ANDANDROIDTheme

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

@Composable
fun MainScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    var registeredEmail by remember { mutableStateOf("") }
    var registeredPassword by remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn") {
            SignInScreen(
                navController = navController,
                registeredEmail = registeredEmail,
                registeredPassword = registeredPassword,
                snackbarHostState = snackbarHostState,
                onSignUpClick = {
                    navController.navigate("signUp")
                },
                onSignInSuccess = { email ->
                    registeredEmail = email
                    navController.navigate(BottomNavItem.Home.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
            )
        }
        composable("signUp") {
            SignUpScreen(
                navController,
                modifier = Modifier,
                onSignUpSuccess = { email, password ->
                    registeredEmail = email
                    registeredPassword = password
                    navController.popBackStack()
                }
            )
        }

        composable("home") {
            BottomNavigation(
                registeredEmail,
                modifier = Modifier
            )
        }
    }
}