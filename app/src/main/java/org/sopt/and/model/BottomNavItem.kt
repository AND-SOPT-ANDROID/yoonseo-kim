package org.sopt.and.model

import org.sopt.and.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

enum class BottomNavItem(val route: String, val icon: @Composable () -> Unit, val title: String) {
    Home("home", { painterResource(id = R.drawable.ic_alarm_24) }, "Home"),
    Search("search", { painterResource(id = R.drawable.ic_alarm_24) }, "Search"),
    MyPage("mypage", { painterResource(id = R.drawable.ic_alarm_24) }, "My Page")
}