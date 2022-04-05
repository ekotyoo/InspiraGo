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
    val savedQuotes: Flow<List<Quote>> = quoteLocalDataSource.quotes
    val quoteForDisplay: Flow<Quote?> = quoteLocalDataSource.quoteToDisplay

    suspend fun refreshQuote() {
        try {
            val response = quoteRemoteDataSource.getRandomQuote()
            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    quoteLocalDataSource.saveQuote(
                        Quote(
                            id = 1,
                            author = responseBody.author ?: "",
                            content = responseBody.content ?: ""
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

    suspend fun saveQuote(quote: Quote) {
        quoteLocalDataSource.saveQuote(quote)
    }

    suspend fun deleteQuote(quote: Quote) {
        quoteLocalDataSource.deleteQuote(quote)
    }
}