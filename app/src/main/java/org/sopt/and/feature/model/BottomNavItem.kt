package org.sopt.and.feature.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.sopt.and.R
import org.sopt.and.core.utils.KeyStorage.HOME
import org.sopt.and.core.utils.KeyStorage.MY_PAGE
import org.sopt.and.core.utils.KeyStorage.SEARCH

enum class BottomNavItem(val route: String, val iconResId: Int) {
    Home(HOME, R.drawable.ic_home_24),
    Search(SEARCH, R.drawable.ic_search_24),
    MyPage(MY_PAGE, R.drawable.ic_my_24);

    @Composable
    fun getTitle(): String {
        return when (this) {
            Home -> stringResource(R.string.home_bottom_nav_item)
            Search -> stringResource(R.string.search_bottom_nav_item)
            MyPage -> stringResource(R.string.my_page_bottom_nav_item)
        }
    }
}