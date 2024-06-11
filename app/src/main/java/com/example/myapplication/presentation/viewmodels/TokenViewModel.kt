package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.TokenRepository
import kotlinx.coroutines.launch

class TokenViewModel(private val tokenRepository: TokenRepository) : ViewModel() {

 //   private val tokenRepository = TokenRepository()

    fun sendToken(token: String, userId: Int) {
        viewModelScope.launch {
            tokenRepository.sendTokenToServer(token, userId)
        }
    }
    class Factory(private val tokenRepository: TokenRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TokenViewModel(tokenRepository) as T
        }
    }
}