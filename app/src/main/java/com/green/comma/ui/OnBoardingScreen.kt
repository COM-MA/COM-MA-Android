package com.green.comma.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.green.comma.R
@Preview(showBackground = false)
@Composable
fun OnBoardingScreen() {
    var imgLogo = painterResource(id = R.drawable.img_onboarding_logo)
    Image(painter = imgLogo, contentDescription = "logo")
}