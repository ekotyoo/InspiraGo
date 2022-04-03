package com.ekotyoo.inspirago.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.ekotyoo.inspirago.data.entity.QuoteEntity
import com.ekotyoo.inspirago.data.repositories.QuoteRepository
import com.ekotyoo.inspirago.di.Injection
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class HomeViewModel(
    private val quoteRepository: QuoteRepository
) : ViewModel() {
    private val _currentQuote = MutableLiveData<QuoteEntity>()
    val currentQuote: LiveData<QuoteEntity> = _currentQuote

    private val _isFirstLaunch = MutableLiveData(true)
    val isFirstLaunch: LiveData<Boolean> =_isFirstLaunch
    fun setIsFirstLaunch(state: Boolean) {
        _isFirstLaunch.value = state
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        getRandomQuote()
    }

    fun getRandomQuote() {
        viewModelScope.launch {
            try {
                val response = quoteRepository.getRandomQuote()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) _currentQuote.value = responseBody.toEntity()
                }  else {
                    val message = when (response.code()) {
                        400 -> "Bad Request"
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        408 -> "Request Timeout"
                        else -> "Try Again Later"
                    }
                    _errorMessage.value = message
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "getRandomQuote: ${e.message}")
                _errorMessage.value = "Something went wrong, try again later"
            }
        }
    }
}

class HomeViewModelFactory private constructor(private val quoteRepository: QuoteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(quoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}