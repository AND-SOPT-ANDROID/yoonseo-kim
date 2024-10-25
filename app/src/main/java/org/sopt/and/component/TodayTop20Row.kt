package org.sopt.and.component

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
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R

@Composable
fun TodayTop20Row(
    modifier: Modifier = Modifier
) {
    val images = listOf(
        R.drawable.ic_poster7,
        R.drawable.ic_poster6,
        R.drawable.ic_poster5,
        R.drawable.ic_poster10,
        R.drawable.ic_poster9,
        R.drawable.ic_poster8,
        R.drawable.ic_poster1,
        R.drawable.ic_poster2,
        R.drawable.ic_poster3,
        R.drawable.ic_poster4,
        R.drawable.ic_poster7,
        R.drawable.ic_poster6,
        R.drawable.ic_poster5,
        R.drawable.ic_poster10,
        R.drawable.ic_poster9,
        R.drawable.ic_poster8,
        R.drawable.ic_poster1,
        R.drawable.ic_poster2,
        R.drawable.ic_poster3,
        R.drawable.ic_poster4
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "오늘의 TOP 20",
            fontSize = 20.sp,
            fontWeight = Bold,
            color = Color.White,
            lineHeight = 30.sp,
        )

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = "More icon",
        )
    }

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
                    contentDescription = "Poster Image",
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = "${index + 1}",
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