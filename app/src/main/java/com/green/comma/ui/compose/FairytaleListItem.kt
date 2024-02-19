package com.green.comma.ui.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.green.comma.data.response.fairytale.ResponseFairytaleListDto
import com.green.comma.ui.compose.theme.Gray500
import com.green.comma.ui.compose.theme.Gray700
import com.green.comma.ui.compose.theme.Green200
import com.green.comma.ui.compose.theme.Green500
import com.green.comma.ui.compose.theme.Orange200
import com.green.comma.ui.compose.theme.Orange500
import com.green.comma.ui.compose.theme.Typography
import com.green.comma.ui.compose.theme.pretendard
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FairytaleListItem(data: ResponseFairytaleListDto, onClick: () -> Unit, modifier: Modifier) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val time: LocalTime = LocalTime.parse(data.time, formatter)

    val info = data.channelName + " | " + data.year + " | " + time.minute + "분"

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(124.dp)
            .padding(horizontal = 30.dp, vertical = 6.dp)
            .shadow(elevation = 4.dp,
                ambientColor = Gray500,
                spotColor = Gray500
            ),
        color = Color.White,
        onClick = onClick
    ) {
        Row(modifier = modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = modifier.height(80.dp).width(80.dp),
                model = data.imageUrl,
                contentScale = ContentScale.Fit,
                contentDescription = "동화 이미지"
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ){
                Row(modifier = modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = modifier.padding(end = 8.dp),
                        maxLines = 1,
                        text = data.title,
                        style = Typography.labelMedium
                    )
                    if(data.subtitleTag)
                        FairytaleTagElem("자막", Orange200, Orange500)
                    if(data.signTag)
                        FairytaleTagElem("수화", Green200, Green500)
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
