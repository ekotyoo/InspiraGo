package com.ekotyoo.inspirago.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ekotyoo.inspirago.data.datasource.remote.QuoteRemoteDataSource
import com.ekotyoo.inspirago.data.Result
import com.ekotyoo.inspirago.data.datasource.remote.QuoteApi
import com.ekotyoo.inspirago.data.entity.QuoteEntity
import java.lang.Exception

class QuoteRepository(
    private val quoteRemoteDataSource: QuoteRemoteDataSource
) {
    suspend fun getRandomQuote() = quoteRemoteDataSource.getRandomQuote()
}