package org.sopt.and.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import org.sopt.and.R
import org.sopt.and.feature.home.HomeViewModel

@Composable
fun AutoSlidingRow(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {
    val images = homeViewModel.autoSlidingImages
    var currentIndex by remember { mutableStateOf(0) }
    val totalImages = images.size

    LaunchedEffect(currentIndex) {
        delay(2000)
        currentIndex = (currentIndex + 1) % images.size
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
            .background(colorResource(R.color.background_gray)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = images[currentIndex]),
            contentDescription = "Auto sliding posters row",
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-40).dp, y = (-20).dp)
                .background(colorResource(R.color.background_gray), shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${currentIndex + 1} | $totalImages",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}