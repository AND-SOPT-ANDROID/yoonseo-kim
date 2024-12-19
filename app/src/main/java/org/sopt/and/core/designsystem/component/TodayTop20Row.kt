package org.sopt.and.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.R
import org.sopt.and.feature.home.HomeViewModel

@Composable
fun TodayTop20Row(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {
    val images = homeViewModel.todayTop20Images

    HomeRowItemTitle(
        titleResId = R.string.today_top_20_row_title
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        itemsIndexed(images) { index, imageResId ->
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(300.dp)
                    .padding(horizontal = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = stringResource(R.string.home_poster_image_description),
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = stringResource(R.string.today_top_20_row_item_index, index + 1),
                    fontSize = 60.sp,
                    fontWeight = Bold,
                    fontStyle = Italic,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }
        }
    }
}