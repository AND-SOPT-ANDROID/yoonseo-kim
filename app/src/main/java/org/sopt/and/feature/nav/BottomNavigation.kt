package org.sopt.and.feature.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.and.R
import org.sopt.and.feature.home.HomeScreen
import org.sopt.and.feature.mypage.MyPageScreen
import org.sopt.and.feature.search.SearchScreen
import org.sopt.and.model.BottomNavItem
import org.sopt.and.ui.theme.ANDANDROIDTheme

@Composable
fun BottomNavigation(
    registeredEmail: String,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.MyPage
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black
            ) {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_alarm_24),
                                contentDescription = "Bottom Navigation icon",
                            )
                        },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Search.route) { SearchScreen() }
            composable(BottomNavItem.MyPage.route) { MyPageScreen(registeredEmail = registeredEmail) }
        }
    }
}