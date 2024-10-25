package org.sopt.and.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import org.sopt.and.R
import org.sopt.and.component.AutoSlidingRow
import org.sopt.and.component.HomeTopBar
import org.sopt.and.component.RecommendationRow
import org.sopt.and.component.TabMenu
import org.sopt.and.component.TodayTop20Row

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTopBar()

        TabMenu()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AutoSlidingRow()
            }

            item {
                RecommendationRow()
            }

            item {
                TodayTop20Row()
            }
        }
    }
}