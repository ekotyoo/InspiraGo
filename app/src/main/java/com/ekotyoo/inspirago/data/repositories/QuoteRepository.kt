package com.ekotyoo.inspirago.data.repositories

import com.ekotyoo.inspirago.data.datasource.local.QuoteLocalDataSource
import com.ekotyoo.inspirago.data.datasource.remote.QuoteRemoteDataSource
import com.ekotyoo.inspirago.data.entity.Quote
import com.ekotyoo.inspirago.utils.QuoteError
import kotlinx.coroutines.flow.Flow

class QuoteRepository(
    private val quoteRemoteDataSource: QuoteRemoteDataSource,
    private val quoteLocalDataSource: QuoteLocalDataSource
) {
    // User's saved quotes
    val savedQuotes: Flow<List<Quote>> = quoteLocalDataSource.quotes

    // Quote to display on home
    val quoteForDisplay: Flow<Quote?> = quoteLocalDataSource.quoteToDisplay

    /**
     * Refresh quote by fetching quote from network
     * then save the result to room database
     */
    suspend fun refreshQuote() {
        try {
            val response = quoteRemoteDataSource.getRandomQuote()
            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    quoteLocalDataSource.saveQuote(
                        Quote(
                            id = 1,
                            author = responseBody.author ?: "Empty",
                            content = responseBody.content ?: "Empty"
                        )
                    )
                }
            } else {
                throw QuoteError("Failed, try again later!", null)
            }
        } catch (cause: Throwable) {
            throw QuoteError("Failed, try again later!", cause)
        }
    }

    /**
     * Save quote to room database
     *
     * @param quote to be saved
     */
    suspend fun saveQuote(quote: Quote) {
        quoteLocalDataSource.saveQuote(quote)
    }

    /**
     * Delete quote from room database
     *
     * @param quote to be deleted
     */
    suspend fun deleteQuote(quote: Quote) {
        quoteLocalDataSource.deleteQuote(quote)
    }
}