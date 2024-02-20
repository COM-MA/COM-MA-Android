package com.green.comma.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PreviewFairytaleListItem(imgUrl: String?){
    Surface(
        modifier = Modifier
            .width(350.dp)
            .height(140.dp)
            .padding(end = 18.dp)
            .clickable {  },
        color = Color.White,
        onClick = {}
    ) {
        AsyncImage(
            contentScale = ContentScale.Fit,
            model = imgUrl,
            contentDescription = "동화 썸네일"
        )
    }
}