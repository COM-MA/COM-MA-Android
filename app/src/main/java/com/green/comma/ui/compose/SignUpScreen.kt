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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.green.comma.R
import com.green.comma.ui.theme.Gray100
import com.green.comma.ui.theme.Gray700
import com.green.comma.ui.theme.Gray900
import com.green.comma.ui.theme.Lavender500
import com.green.comma.ui.theme.Typography

@Composable
fun SignUpScreen(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.horizontal_padding)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(top = 90.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 21.dp),
                text = "사용하실\n별명을 알려주세요!",
                style = Typography.headlineMedium,
                lineHeight = 45.sp, color = Gray900
            )
            Text(modifier = Modifier.padding(bottom = 8.dp), text = "내 별명", style = Typography.bodySmall, color = Gray700)
            NicknameTextField()
        }
        Surface(
            modifier = Modifier.padding(bottom = 100.dp)
        ) {
            CompleteBtn(onClick)
        }
    }
}

@Composable
fun NicknameTextField() {
    val imgLogo = painterResource(id = R.drawable.icon_textfield_x)
    var text by remember {mutableStateOf("")}   // 기본 랜덤 별명

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        color = Gray100
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = text,
                onValueChange = { newValue -> text = newValue},
                textStyle = Typography.bodyMedium
            )
            Image(modifier = Modifier
                .fillMaxSize()
                .padding(12.dp), painter = imgLogo, contentDescription = "x button")
        }
    }
}

@Composable
fun CompleteBtn(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = Lavender500
    ) {
        Text(
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
            text = "완료했어요!",
            style = Typography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Center)
    }
}