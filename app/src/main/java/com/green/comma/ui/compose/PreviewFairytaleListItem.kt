package com.green.comma.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun PreviewFairytaleListItem(img: Painter){
    Surface(
        modifier = Modifier
            .width(294.dp)
            .height(124.dp)
            .clickable {  }
            .padding(10.dp),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        onClick = {}
    ) {
        Image(contentScale = ContentScale.FillWidth, painter = img, contentDescription = "동화 썸네일")
    }
}