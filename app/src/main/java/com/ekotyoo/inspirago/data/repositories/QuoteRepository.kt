package com.ekotyoo.inspirago.data.repositories

import com.ekotyoo.inspirago.data.datasource.local.QuoteLocalDataSource
import com.ekotyoo.inspirago.data.datasource.remote.QuoteRemoteDataSource
import com.ekotyoo.inspirago.data.entity.Quote
import kotlinx.coroutines.flow.Flow

class QuoteRepository(
    private val quoteRemoteDataSource: QuoteRemoteDataSource,
    private val quoteLocalDataSource: QuoteLocalDataSource
) {
    val savedQuotes: Flow<List<Quote>> = quoteLocalDataSource.quotes

    suspend fun getRandomQuote() = quoteRemoteDataSource.getRandomQuote()
}