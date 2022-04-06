package com.ekotyoo.inspirago.data.datasource.local

import com.ekotyoo.inspirago.data.entity.Quote
import kotlinx.coroutines.flow.Flow

class QuoteLocalDataSource(
    private val quoteDao: QuoteDao
) {
    // User's saved quotes
    val quotes: Flow<List<Quote>> = quoteDao.getAllQuote()

    // Quote to be displayed on home
    val quoteToDisplay: Flow<Quote> = quoteDao.getQuoteToDisplay()

    /**
     * Save quote to room database
     *
     * @param quote to be saved
     */
    suspend fun saveQuote(quote: Quote) {
        quoteDao.saveQuote(quote)
    }

    /**
     * Delete quote from room database
     *
     * @param quote to be deleted
     */
    suspend fun deleteQuote(quote: Quote) {
        quoteDao.deleteQuote(quote.author, quote.content)
    }
}