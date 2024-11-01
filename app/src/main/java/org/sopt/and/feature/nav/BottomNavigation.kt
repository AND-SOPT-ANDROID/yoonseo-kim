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
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.and.R
import org.sopt.and.feature.home.HomeScreen
import org.sopt.and.feature.mypage.MyPageScreen
import org.sopt.and.feature.mypage.MyPageViewModel
import org.sopt.and.feature.search.SearchScreen
import org.sopt.and.model.BottomNavItem
import org.sopt.and.utils.KeyStorage.HOME
import org.sopt.and.utils.KeyStorage.MY_PAGE
import org.sopt.and.utils.KeyStorage.SEARCH

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
                                contentDescription = stringResource(R.string.bottom_navigation_description),
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
            startDestination = HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HOME) {
                HomeScreen()
            }
            composable(SEARCH) {
                SearchScreen()
            }
            composable(MY_PAGE) {
                MyPageScreen(registeredEmail = registeredEmail, viewModel = MyPageViewModel())
            }
        }
    }
}