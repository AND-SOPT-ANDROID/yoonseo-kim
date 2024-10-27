package org.sopt.and.model

import org.sopt.and.R
import org.sopt.and.utils.KeyStorage.HOME
import org.sopt.and.utils.KeyStorage.MY_PAGE
import org.sopt.and.utils.KeyStorage.SEARCH

enum class BottomNavItem(val route: String, val iconResId: Int, val title: String) {
    Home(HOME, R.drawable.ic_home_24, "홈"),
    Search(SEARCH, R.drawable.ic_search_24, "검색"),
    MyPage(MY_PAGE, R.drawable.ic_my_24, "MY")
}