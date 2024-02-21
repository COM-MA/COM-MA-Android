package com.green.comma.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageButton
import com.green.comma.R
import java.util.Locale

class Tts(val context: Context) {
    private var textToSpeech: TextToSpeech? = null
    fun setTTS(){
        textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener {
            if(it == TextToSpeech.SUCCESS){
                val result = textToSpeech!!.setLanguage(Locale.KOREA)
                textToSpeech?.setSpeechRate(1.2f)
                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTS", "해당언어는 지원되지 않습니다.")
                    return@OnInitListener
                }
            }
        })
        Log.e("TTS", "TTS 세팅 완료")
    }

    fun readTTS(word: String, button: ImageButton){
        button.setImageResource(R.drawable.ic_word_card_speaker_lavender)
        textToSpeech?.speak(word, TextToSpeech.QUEUE_FLUSH, null)
        Handler(Looper.getMainLooper()).postDelayed({
            button.setImageResource(R.drawable.ic_word_card_speaker_gray)
        }, 1000)
    }
}