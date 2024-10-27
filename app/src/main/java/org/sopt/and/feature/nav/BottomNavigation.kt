package org.sopt.and.feature.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.and.R
import org.sopt.and.feature.home.HomeScreen
import org.sopt.and.feature.mypage.MyPageScreen
import org.sopt.and.feature.mypage.MyPageViewModel
import org.sopt.and.feature.search.SearchScreen
import org.sopt.and.model.BottomNavItem

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
                containerColor = colorResource(R.color.background_gray)
            ) {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconResId),
                                contentDescription = "Bottom Navigation icon",
                            )
                        },
                        label = { Text(text = item.getTitle()) },
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
            composable(BottomNavItem.Home.route) {
                HomeScreen()
            }
            composable(BottomNavItem.Search.route) {
                SearchScreen()
            }
            composable(BottomNavItem.MyPage.route) {
                MyPageScreen(registeredEmail = registeredEmail, viewModel = MyPageViewModel())
            }
        }
    }
}