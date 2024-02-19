package com.green.comma.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.ui.theme.Gray500
import com.green.comma.ui.theme.Lavender200
import com.green.comma.ui.theme.Typography
import com.green.comma.ui.theme.pretendard

@Composable
fun WordCardListItem(data: ResponseCardListDto, onClick: () -> Unit, modifier: Modifier = Modifier){
    Surface(
        modifier = modifier
            .width(162.dp)
            .height(195.dp)
            .padding(10.dp)
            .shadow(
                shape = RoundedCornerShape(12.dp),
                elevation = 6.dp,
                ambientColor = Gray500,
                spotColor = Gray500
            ),
        color = Color.White,
        onClick = onClick
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            AsyncImage(
                modifier = modifier.height(77.dp),
                model = data.cardImageUrl,
                contentDescription = "단어 이미지"
            )
            //Image(modifier = modifier.height(77.dp), painter = img, contentDescription = "단어 이미지")
            Text(modifier = modifier.padding(0.dp, 6.dp), text = data.name, style = Typography.labelLarge)
            Surface(
                modifier = modifier
                    .width(80.dp)
                    .height(27.dp)
                    .clickable(onClick = {}),
                shape = RoundedCornerShape(8.dp),
                color = Lavender200
            ) {
                Text(
                    modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically),
                    text = "뒤집기",
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center)
            }
        }
    }
}