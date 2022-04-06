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

    val currentQuote: MediatorLiveData<Quote> = MediatorLiveData<Quote>()
    val allQuote: LiveData<List<Quote>> = quoteRepository.savedQuotes.asLiveData()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Initialize current quote to be displayed
     */
    init {
        currentQuote.addSource(quoteRepository.quoteForDisplay.asLiveData()) {
            if (it == null) {
                refreshQuote()
            } else {
                currentQuote.value = it
            }
        }
    }

    /**
     * Refresh current quote with new quote from network
     */
    fun refreshQuote() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                quoteRepository.refreshQuote()
            } catch (error: QuoteError) {
                Log.d("HomeViewModel", "getRandomQuote: ${error.message}")
                _errorMessage.value = error.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Save current quote [currentQuote] to room database
     */
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

    /**
     * Delete single quote from room database
     *
     * @param quote to be deleted
     */
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

/**
 * ViewModelFactory for HomeViewModel
 */
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