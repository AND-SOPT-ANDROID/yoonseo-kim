package org.sopt.and.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.R
import org.sopt.and.core.designsystem.component.AutoSlidingRow
import org.sopt.and.core.designsystem.component.HomeTopBar
import org.sopt.and.core.designsystem.component.RecommendationRow
import org.sopt.and.core.designsystem.component.TabMenu
import org.sopt.and.core.designsystem.component.TodayTop20Row

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
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
                AutoSlidingRow(
                    images = viewModel.autoSlidingImages
                )
            }

            item {
                RecommendationRow(
                    images = viewModel.recommendationImages
                )
            }

            item {
                TodayTop20Row()
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}