package org.sopt.and.component

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R

@Composable
fun TabMenu(
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabItems = listOf(
        stringResource(R.string.tab_menu_new_classic_title),
        stringResource(R.string.tab_menu_drama_title),
        stringResource(R.string.tab_menu_entertain_title),
        stringResource(R.string.tab_menu_movie_title),
        stringResource(R.string.tab_menu_animation_title),
        stringResource(R.string.tab_menu_global_series_title)
    )

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        containerColor = colorResource(R.color.background_gray),
        contentColor = Color.White,
        indicator = {},
        divider = {}
    ) {
        tabItems.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        color = if (selectedTabIndex == index) Color.White else Color.Gray
                    )
                }
            )
        }
    }
}