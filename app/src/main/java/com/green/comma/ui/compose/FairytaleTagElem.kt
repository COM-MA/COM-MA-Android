package com.green.comma.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.green.comma.ui.compose.theme.pretendard

@Composable
fun FairytaleTagElem(tag: String, colorBg: Color, colorTxt: Color){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
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