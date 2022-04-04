package com.ekotyoo.inspirago.data.datasource.local

import com.ekotyoo.inspirago.data.entity.Quote
import kotlinx.coroutines.flow.Flow

class QuoteLocalDataSource(
    private val quoteDao: QuoteDao
) {
    val quotes: Flow<List<Quote>> = quoteDao.getAllQuote()

    suspend fun saveQuote(quote: Quote) {
        quoteDao.saveQuote(quote)
    }
}