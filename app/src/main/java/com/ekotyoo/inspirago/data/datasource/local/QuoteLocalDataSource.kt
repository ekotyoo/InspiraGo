package com.ekotyoo.inspirago.data.datasource.local

import com.ekotyoo.inspirago.data.entity.Quote
import kotlinx.coroutines.flow.Flow

class QuoteLocalDataSource(
    private val quoteDao: QuoteDao
) {
    val quotes: Flow<List<Quote>> = quoteDao.getAllQuote()
    val quoteToDisplay: Flow<Quote> = quoteDao.getQuoteToDisplay()

    suspend fun saveQuote(quote: Quote) {
        quoteDao.saveQuote(quote)
    }

    suspend fun deleteQuote(quote: Quote) {
        quoteDao.deleteQuote(quote.author, quote.content)
    }
}