package com.green.comma.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.green.comma.R
import com.green.comma.ui.theme.Lavender200
import com.green.comma.ui.theme.pretendard
@Composable
fun WordCardView(onClick: () -> Unit, wordText: String) {
    val img = painterResource(id = R.drawable.ic_bottom_nav_camera)
    val imgVolume = painterResource(id = R.drawable.ic_word_card_speaker_lavender)
    val imgStar = painterResource(id = R.drawable.ic_word_card_speaker_lavender)

    Surface(
        modifier = Modifier
            .width(350.dp)
            .height(504.dp)
            .padding(10.dp),
        color = Color.White,
        shape = RoundedCornerShape(28.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(modifier = Modifier.padding(bottom = 15.dp).clickable(onClick = onClick), painter = imgVolume, contentDescription = "스피커")
            Image(modifier = Modifier.height(180.dp), painter = img, contentDescription = "단어 이미지")
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 30.dp),
                text = wordText,
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                ))
            Button(
                modifier = Modifier.width(140.dp).height(48.dp),
                shape = RoundedCornerShape(14.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Lavender200, contentColor = Color.White),
                content = {
                    Text(
                        text = "뒤집기",
                        style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center)
                }
            )
        }
    }
}