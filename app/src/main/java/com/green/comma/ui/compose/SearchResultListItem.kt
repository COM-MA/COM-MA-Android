package com.green.comma.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.green.comma.ui.compose.theme.pretendard
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.green.comma.ui.compose.theme.Gray100
import com.green.comma.ui.compose.theme.Gray300
import com.green.comma.ui.compose.theme.Gray700
import com.green.comma.ui.compose.theme.Lavender500

@Composable
fun SearchResultListItem(searchText: String, title: String, descr: String, form: String, modifier: Modifier) {

    val title = setResultText(title, searchText)

    Surface(
        modifier = modifier
            .padding(bottom = 2.dp)
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 2.dp,
                ambientColor = Gray300,
                spotColor = Gray300
            ),
        color = Color.White,
    ) {
        Row(
            modifier = modifier.fillMaxSize().padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = modifier.padding(bottom = 5.dp),
                    text = title,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black,
                    )
                )
                Text(
                    text = descr,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Gray700,
                    )
                )
            }
            Button(
                modifier = modifier.padding(4.dp),
                colors = ButtonDefaults.buttonColors(Gray100),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(14.dp, 0.dp),
                onClick = {}
            ) {
                Text(
                    text = form,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, Gray300),
                contentPadding = PaddingValues(14.dp, 0.dp),
                onClick = {}
            ) {
                Text(
                    text = "상세보기",
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

fun setResultText(resultText: String, searchText: String): AnnotatedString {

    val start = resultText.indexOf(searchText)
    val end = start + searchText.length

    val front = resultText.substring(0, start)
    val back = resultText.substring(end)

    val text = buildAnnotatedString {
        append(front)
        withStyle(SpanStyle(color = Lavender500)) {
            append(searchText)
        }
        append(back)
    }

    /*val spannableString = buildAnnotatedString {  }(resultText)
    spannableString.setSpan(ForegroundColorSpan(Lavender500.hashCode()), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
*/
    return text
}