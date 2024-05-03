package com.green.comma.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.green.comma.ui.compose.theme.Gray500
import com.green.comma.ui.compose.theme.pretendard
import com.green.comma.ui.emoji.EmojiControl
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiBottomSheet(visibility: Boolean, modifier: Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


    if (visibility) {
        ModalBottomSheet(
            onDismissRequest = {
                EmojiControl.setBottomSheetActive(false)
            },
            sheetState = sheetState,
            scrimColor = Color.Transparent,

        ) {
            LazyVerticalGrid(
                modifier = modifier.padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
                columns = GridCells.Fixed(3)
            ) {
                items(EmojiControl.emojiList.size) { idx ->
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    EmojiControl.setSelectedEmoji(idx)
                                    EmojiControl.setBottomSheetActive(false)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                modifier = modifier
                                    .height(86.dp)
                                    .width(86.dp),
                                model = EmojiControl.emojiList[idx].img,
                                contentScale = ContentScale.Fit,
                                contentDescription = stringResource(id = EmojiControl.emojiList[idx].name),
                            )
                            Text(
                                modifier = modifier.padding(top = 6.dp),
                                text = stringResource(id = EmojiControl.emojiList[idx].name),
                                style = TextStyle(
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Gray500
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
