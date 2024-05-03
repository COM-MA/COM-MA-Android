package com.green.comma.ui.emoji

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.repository.EmojiRepository
import com.green.comma.data.repository.FairytaleRepository
import com.green.comma.data.request.emoji.RequestEmotionDto
import com.green.comma.data.response.fairytale.ResponseFairytaleDetailDto
import kotlinx.coroutines.launch

class EmojiViewModel(private val emojiRepository: EmojiRepository) : ViewModel() {
    private val _emojiPostResult = MutableLiveData<Boolean>()
    val emojiPostResult: LiveData<Boolean> = _emojiPostResult

    fun postEmoji(data: RequestEmotionDto) {
        viewModelScope.launch {
            val emojiPostResult = emojiRepository.postEmoji(data)
            _emojiPostResult.value = emojiPostResult
        }
    }
}