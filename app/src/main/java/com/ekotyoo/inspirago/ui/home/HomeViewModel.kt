package com.ekotyoo.inspirago.ui.home

import androidx.lifecycle.*
import com.ekotyoo.inspirago.data.entity.QuoteEntity
import com.ekotyoo.inspirago.data.repositories.QuoteRepository
import com.ekotyoo.inspirago.di.Injection
import kotlinx.coroutines.launch

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

    init {
        getRandomQuote()
    }

    fun getRandomQuote() {
        viewModelScope.launch {
            val response = quoteRepository.getRandomQuote()
            _currentQuote.value = response.toEntity()
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