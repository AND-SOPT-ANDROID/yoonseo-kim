package org.sopt.and.feature.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.sopt.and.feature.home.HomeScreen
import org.sopt.and.feature.mypage.MyPageScreen
import org.sopt.and.feature.mypage.MyPageViewModel
import org.sopt.and.feature.search.SearchScreen
import org.sopt.and.utils.KeyStorage.HOME
import org.sopt.and.utils.KeyStorage.MY_PAGE
import org.sopt.and.utils.KeyStorage.SEARCH

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

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
            MyPageScreen(viewModel = MyPageViewModel(context))
        }
    }
}