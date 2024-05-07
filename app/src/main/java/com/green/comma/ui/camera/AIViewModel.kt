package com.green.comma.ui.camera

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.repository.AIRepository
import com.green.comma.data.response.card.ResponseCardListDto
import com.green.comma.data.response.ai.ResponsePredictionDto
import com.green.comma.ui.common.LoadingAIDialog
import com.green.comma.ui.common.LoadingDialog
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AIViewModel(private val aiRepository: AIRepository) : ViewModel() {

    private val _prediction = MutableLiveData<ResponsePredictionDto>()
    val prediction: LiveData<ResponsePredictionDto> = _prediction

    fun postPrediction(file: MultipartBody.Part, context: Context): Boolean {
        val loadingAIDialog = LoadingAIDialog(context)
        var isSuccess = false
        viewModelScope.launch {
            loadingAIDialog.show()
            aiRepository.postSignPrediction(file)
                .onSuccess {
                    _prediction.value = it
                    loadingAIDialog.dismiss()
                    isSuccess = true
                }
                .onFailure {
                    Log.d("GET AI PREDICTION DATA FAILURE", it.toString())
                    Toast.makeText(context, "인식에 실패했어요.", Toast.LENGTH_SHORT).show()
                    loadingAIDialog.dismiss()
                    isSuccess = false
                }
        }
        return isSuccess
    }
}