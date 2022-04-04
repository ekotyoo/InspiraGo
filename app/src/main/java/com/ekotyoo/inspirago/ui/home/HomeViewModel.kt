package com.ekotyoo.inspirago.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.ekotyoo.inspirago.data.entity.Quote
import com.ekotyoo.inspirago.data.repositories.QuoteRepository
import com.ekotyoo.inspirago.di.Injection
import com.ekotyoo.inspirago.utils.QuoteError
import kotlinx.coroutines.launch

class HomeViewModel(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    val currentQuote: LiveData<Quote> = quoteRepository.quoteForDisplay.asLiveData()
    val allQuote: LiveData<List<Quote>> = quoteRepository.savedQuotes.asLiveData()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        refreshQuote()
    }

    fun refreshQuote() {
        viewModelScope.launch {
            try {
                quoteRepository.refreshQuote()
            } catch (error: QuoteError) {
                Log.d("HomeViewModel", "getRandomQuote: ${error.message}")
                _errorMessage.value = error.message
            }
        }
    }

    fun saveQuote() {
        viewModelScope.launch {
            try {
                val quote = currentQuote.value
                quoteRepository.saveQuote(quote!!.copy(id = null, isBookmarked = true))
            } catch (error: Throwable) {
                _errorMessage.value = error.message
            }
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch {
            try {
                quoteRepository.deleteQuote(quote)
            } catch (error: Throwable) {
                _errorMessage.value = error.message
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
        fun getInstance(context: Context): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}