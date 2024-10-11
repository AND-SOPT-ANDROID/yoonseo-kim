package org.sopt.and.mypage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R
import org.sopt.and.ui.theme.ANDANDROIDTheme

class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val registeredEmail = intent.getStringExtra("email") ?: ""

        setContent {
            ANDANDROIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyPage(
                        name = registeredEmail,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyPage(name: String, modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF252525)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(40.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(color = Color(0xFF252525)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "profile image",
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(start = 20.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "${name}님",
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
            color = Color(0xFFA7A7A7),
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
            color = Color(0xFF1B1B1B)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.my_page_no_license),
            fontSize = 16.sp,
            color = Color(0xFFA7A7A7),
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
                .background(Color(0xFF1B1B1B))
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.my_page_whole_history),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 30.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.ic_exclamation),
                contentDescription = "logo images",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(70.dp)
            )

            Text(
                text = stringResource(R.string.my_page_no_history),
                fontSize = 12.sp,
                color = Color(0xFFA7A7A7),
                lineHeight = 30.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.my_page_interest_program),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 30.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_exclamation),
                contentDescription = "logo images",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(70.dp)
            )

            Text(
                text = stringResource(R.string.my_page_no_interest_program),
                fontSize = 12.sp,
                color = Color(0xFFA7A7A7),
                lineHeight = 30.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    ANDANDROIDTheme {
        MyPage(stringResource(R.string.my_page_profile_name))
    }
}