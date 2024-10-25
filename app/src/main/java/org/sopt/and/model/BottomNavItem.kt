package org.sopt.and.model

import org.sopt.and.R

enum class BottomNavItem(val route: String, val iconResId: Int, val title: String) {
    Home("home", R.drawable.ic_home_24, "홈"),
    Search("search", R.drawable.ic_search_24, "검색"),
    MyPage("mypage", R.drawable.ic_my_24, "MY")
}