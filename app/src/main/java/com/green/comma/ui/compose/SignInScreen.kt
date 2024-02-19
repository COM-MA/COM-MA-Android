package com.green.comma.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.green.comma.R
import com.green.comma.ui.compose.theme.Gray900
import com.green.comma.ui.compose.theme.Typography

@Composable
fun SignInScreen(onClick: () -> Unit) {
    val imgLogo = painterResource(id = R.drawable.img_onboarding_logo)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.horizontal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(180.dp).padding(bottom = 30.dp),
            contentScale = ContentScale.FillWidth,
            painter = imgLogo,
            contentDescription = "Logo"
        )
        SignInBtn(onClick)
    }
}

@Composable
fun SignInBtn(onClick: () -> Unit) {
    val imgGoogle = painterResource(id = R.drawable.icon_signin_google)

    Surface(
        modifier = Modifier.fillMaxWidth().height(40.dp)
            .clickable(onClick = onClick),
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 2.dp) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Image(modifier = Modifier.height(18.dp).padding(end = 17.dp), painter = imgGoogle, contentDescription = null, contentScale = ContentScale.FillHeight)
            Text(modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically), text = "구글로 로그인하기", style = Typography.labelMedium, color = Gray900)
        }
    }
}