package com.green.comma.ui.emoji

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.repository.EmojiRepository
import com.green.comma.data.repository.FairytaleRepository
import com.green.comma.data.request.emoji.RequestEmotionDto
import com.green.comma.data.response.emoji.ResponseEmojiDto
import com.green.comma.data.response.fairytale.ResponseFairytaleDetailDto
import kotlinx.coroutines.launch

class EmojiViewModel(private val emojiRepository: EmojiRepository) : ViewModel() {
    private val _emojiPostResult = MutableLiveData<Boolean>()
    val emojiPostResult: LiveData<Boolean> = _emojiPostResult

    private val _emojiData = MutableLiveData<ResponseEmojiDto?>()
    val emojiData: LiveData<ResponseEmojiDto?> = _emojiData

    fun postEmoji(data: RequestEmotionDto, context: Context) {
        viewModelScope.launch {
            val emojiPostResult = emojiRepository.postEmoji(data)
            _emojiPostResult.value = emojiPostResult
            Toast.makeText(context, "감정 등록에 성공했어요.", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadEmoji() {
        viewModelScope.launch {
            emojiRepository.getEmoji()
                .onSuccess {
                    _emojiData.value = it
                }
                .onFailure {
                    Log.d("GET EMOTICON FAILURE", it.toString())
                }
        }
    }
}