package org.sopt.and.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R
import org.sopt.and.ui.theme.ANDANDROIDTheme

@Composable
fun SignUpTopBar(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF1B1B1B)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_close_24),
            contentDescription = stringResource(R.string.sign_up_close_button_description),
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 10.dp)
        )
    }
}

@Composable
fun SignInTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF1B1B1B)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_wavve_logo),
            contentDescription = stringResource(R.string.sign_in_title_description),
            modifier = Modifier
                .width(100.dp)
                .height(80.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left_24),
            contentDescription = stringResource(R.string.sign_in_left_arrow_button_description),
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 10.dp)
        )
    }
}

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF1B1B1B)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_wavve_logo),
            contentDescription = stringResource(R.string.home_wavve_logo_description),
            modifier = Modifier
                .padding(start = 16.dp)
                .width(100.dp)
                .height(80.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cast_24),
                contentDescription = stringResource(R.string.home_cast_button_description),
                tint = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_live_24),
                contentDescription = stringResource(R.string.home_live_button_description),
                tint = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}