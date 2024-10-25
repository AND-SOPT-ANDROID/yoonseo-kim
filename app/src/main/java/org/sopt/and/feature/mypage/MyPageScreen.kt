package org.sopt.and.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.R
import org.sopt.and.component.MyPageBox

@Composable
fun MyPageScreen(
    registeredEmail: String,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = viewModel()
) {
    LaunchedEffect(registeredEmail) {
        viewModel.initUserName(registeredEmail)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.my_page_top_background)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(40.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(colorResource(R.color.my_page_top_background)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(start = 20.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.my_page_profile_name, registeredEmail),
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_alarm_24),
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 10.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_setting_24),
                contentDescription = "Settings",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.my_page_first_month_description),
            fontSize = 16.sp,
            color = colorResource(R.color.my_page_description_title),
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.my_page_purchase),
            fontSize = 16.sp,
            color = Color.White,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = colorResource(R.color.background_gray)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.my_page_no_license),
            fontSize = 16.sp,
            color = colorResource(R.color.my_page_description_title),
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.my_page_purchase),
            fontSize = 16.sp,
            color = Color.White,
            lineHeight = 30.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .background(colorResource(R.color.background_gray))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MyPageBox(
                title = stringResource(R.string.my_page_whole_history),
                description = stringResource(R.string.my_page_no_history),
                imageResId = R.drawable.ic_exclamation
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyPageBox(
                title = stringResource(R.string.my_page_interest_program),
                description = stringResource(R.string.my_page_no_interest_program),
                imageResId = R.drawable.ic_exclamation
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}