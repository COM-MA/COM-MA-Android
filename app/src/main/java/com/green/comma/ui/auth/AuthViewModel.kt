package com.green.comma.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.green.comma.data.repository.AuthRepository
import com.green.comma.data.response.auth.ResponseGoogleLoginDto
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _googleLoginResult = MutableLiveData<ResponseGoogleLoginDto>()
    val googleLoginResult: LiveData<ResponseGoogleLoginDto> = _googleLoginResult

    fun loadGoogleLogin(code: String) {
        viewModelScope.launch {
            val loginResult = authRepository.postGoogleLogin(code)
            _googleLoginResult.value = loginResult
        }
    }
}