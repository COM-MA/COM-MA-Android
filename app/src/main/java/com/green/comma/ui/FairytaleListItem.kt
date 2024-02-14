package com.green.comma.ui

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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.green.comma.R
import com.green.comma.ui.theme.Gray500
import com.green.comma.ui.theme.Gray700
import com.green.comma.ui.theme.Lavender200
import com.green.comma.ui.theme.Typography
import com.green.comma.ui.theme.pretendard
import java.time.Year

@Composable
fun FairytaleListItem(modifier: Modifier) {
    val ftData = FairytaleData(
        painterResource(R.drawable.ic_bottom_nav_camera),
        "꼭 잡아!", "한국농아인협회", 2024, 6)
    val info = ftData.source + " | " + ftData.year + " | " + ftData.length + "분"

    val orange500 = Color(0xFFFF8E3D)
    val orange200 = Color(0xFFFFEDE0)

    val green500 = Color(0xFF6FD032)
    val green200 = Color(0xFFE8F8DE)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(114.dp)
            .padding(10.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        onClick = { }
    ) {
        Row(modifier = modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(modifier = modifier
                .height(66.dp)
                .width(66.dp), painter = ftData.img, contentScale = ContentScale.Fit, contentDescription = "동화 이미지")
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ){
                Row(modifier = modifier.padding(bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = modifier.padding(end = 4.dp),
                        text = ftData.title,
                        style = Typography.labelMedium
                    )
                    tagElem("자막", orange200, orange500)
                    tagElem("수화", green200, green500)
                }
                Text(
                    text = info,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Gray700
                    ),
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun tagElem(tag: String, colorBg: Color, colorTxt: Color){
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp),
        color = colorBg,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp, 2.dp),
            text = tag,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = colorTxt
            )
        )
    }
}

data class FairytaleData(var img: Painter, var title: String, var source: String, val year: Int, val length: Int)