package org.sopt.and.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R

@Composable
fun RecommendationRow(
    images: List<Int>,
    modifier: Modifier = Modifier
) {
    HomeRowItemTitle(
        titleResId = R.string.recommendation_row_title
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(images) { imageResId ->
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = stringResource(R.string.home_poster_image_description),
                modifier = Modifier
                    .width(180.dp)
                    .height(300.dp)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}