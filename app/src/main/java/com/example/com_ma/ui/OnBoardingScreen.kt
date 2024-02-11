package com.example.com_ma.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.com_ma.R
@Preview(showBackground = false)
@Composable
fun OnBoardingScreen() {
    var imgLogo = painterResource(id = R.drawable.img_onboarding_logo)
    Image(painter = imgLogo, contentDescription = "logo")
}