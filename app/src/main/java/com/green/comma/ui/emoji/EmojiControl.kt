package com.green.comma.ui.emoji

import androidx.lifecycle.MutableLiveData
import com.green.comma.R

object EmojiControl {
    val isChildSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isParentsSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isBottomSheetActive: MutableLiveData<Boolean> = MutableLiveData(false)

    var childEmojiIdx: MutableLiveData<Int> = MutableLiveData(-1)
    var parentsEmojiIdx: MutableLiveData<Int> = MutableLiveData(-1)

    val isCompleted: MutableLiveData<Boolean> = MutableLiveData(false)

    enum class EmojiName {
        SOSO, ANGRY, SAD, PEACEFUL, DESPRESS, HAPPY, FULLFILLED, ANXIOUS, NONE
    }

    data class Emoji(val img: Int, val name: Int, val day: Int, val enum: EmojiName)

    val emojiList = listOf(
        Emoji(R.drawable.img_emoji_soso, R.string.today_feeling_emoji_title_soso, R.string.today_feeling_emoji_day_soso, EmojiName.SOSO),
        Emoji(R.drawable.img_emoji_mad, R.string.today_feeling_emoji_title_mad, R.string.today_feeling_emoji_day_mad, EmojiName.ANGRY),
        Emoji(R.drawable.img_emoji_sad, R.string.today_feeling_emoji_title_sad, R.string.today_feeling_emoji_day_sad, EmojiName.SAD),
        Emoji(R.drawable.img_emoji_calm, R.string.today_feeling_emoji_title_calm, R.string.today_feeling_emoji_day_calm, EmojiName.PEACEFUL),
        Emoji(R.drawable.img_emoji_depressed, R.string.today_feeling_emoji_title_depressed, R.string.today_feeling_emoji_day_depressed, EmojiName.DESPRESS),
        Emoji(R.drawable.img_emoji_happy, R.string.today_feeling_emoji_title_happy, R.string.today_feeling_emoji_day_happy, EmojiName.HAPPY),
        Emoji(R.drawable.img_emoji_proud, R.string.today_feeling_emoji_title_proud, R.string.today_feeling_emoji_day_proud, EmojiName.FULLFILLED),
        Emoji(R.drawable.img_emoji_anxious, R.string.today_feeling_emoji_title_anxious, R.string.today_feeling_emoji_day_anxious, EmojiName.ANXIOUS),
        Emoji(R.drawable.img_emoji_idontknow, R.string.today_feeling_emoji_title_idontknow, R.string.today_feeling_emoji_day_idontknow, EmojiName.NONE),
    )

    /*val emojiList = listOf(
        listOf(R.drawable.img_emoji_soso, R.string.today_feeling_emoji_title_soso, R.string.today_feeling_emoji_day_soso),
        listOf(R.drawable.img_emoji_mad, R.string.today_feeling_emoji_title_mad, R.string.today_feeling_emoji_day_mad),
        listOf(R.drawable.img_emoji_sad, R.string.today_feeling_emoji_title_sad, R.string.today_feeling_emoji_day_sad),
        listOf(R.drawable.img_emoji_calm, R.string.today_feeling_emoji_title_calm, R.string.today_feeling_emoji_day_calm),
        listOf(R.drawable.img_emoji_depressed, R.string.today_feeling_emoji_title_depressed, R.string.today_feeling_emoji_day_depressed),
        listOf(R.drawable.img_emoji_happy, R.string.today_feeling_emoji_title_happy, R.string.today_feeling_emoji_day_happy),
        listOf(R.drawable.img_emoji_proud, R.string.today_feeling_emoji_title_proud, R.string.today_feeling_emoji_day_proud),
        listOf(R.drawable.img_emoji_anxious, R.string.today_feeling_emoji_title_anxious, R.string.today_feeling_emoji_day_anxious),
        listOf(R.drawable.img_emoji_idontknow, R.string.today_feeling_emoji_title_idontknow, R.string.today_feeling_emoji_day_idontknow),
    )*/

    fun setChildSelected() {
        isChildSelected.value = isChildSelected.value != true
        isParentsSelected.value = false
        isBottomSheetActive.value = isChildSelected.value
    }

    fun setParentsSelected() {
        isParentsSelected.value = isParentsSelected.value != true
        isChildSelected.value = false
        isBottomSheetActive.value = isParentsSelected.value
    }

    fun setBottomSheetActive(isActive: Boolean) {
        isBottomSheetActive.value = isActive
        if(!isActive) {
            isChildSelected.value = false
            isParentsSelected.value = false
        }
    }

    fun setSelectedEmoji(idx: Int){
        if(this.isChildSelected.value == true){
            this.childEmojiIdx.value = idx
        }

        if(this.isParentsSelected.value == true){
            this.parentsEmojiIdx.value = idx
        }

        if(this.childEmojiIdx.value!! > -1 && this.parentsEmojiIdx.value!! > -1) {
            this.isCompleted.value = true
        }
    }
}