package org.sopt.and.feature.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.sopt.and.core.utils.KeyStorage.HOME
import org.sopt.and.core.utils.KeyStorage.MY_PAGE
import org.sopt.and.core.utils.KeyStorage.SEARCH
import org.sopt.and.feature.home.HomeScreen
import org.sopt.and.feature.mypage.MyPageScreen
import org.sopt.and.feature.search.SearchScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HOME,
        modifier = Modifier
    ) {
        composable(HOME) {
            HomeScreen()
        }
        composable(SEARCH) {
            SearchScreen()
        }
        composable(MY_PAGE) {
            MyPageScreen()
        }
    }
}