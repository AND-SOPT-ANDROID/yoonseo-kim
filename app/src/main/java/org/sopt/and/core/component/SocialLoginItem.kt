package org.sopt.and.core.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.R

@Composable
fun SocialLoginItem(
    @StringRes textResId: Int,
    @DrawableRes imageResId: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = textResId),
        fontSize = 14.sp,
        color = colorResource(id = R.color.text_default_gray),
        modifier = modifier.padding(horizontal = 15.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = stringResource(R.string.logo_images_description),
        modifier = Modifier.fillMaxWidth()
    )
}