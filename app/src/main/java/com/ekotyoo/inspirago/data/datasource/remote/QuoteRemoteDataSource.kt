package com.ekotyoo.inspirago.data.datasource.remote

import com.ekotyoo.inspirago.data.QuoteResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class QuoteRemoteDataSource(
    private val quoteApi: QuoteApi,
) {
    suspend fun getRandomQuote(): QuoteResponse = quoteApi.getRandomQuote()
}

interface QuoteApi {
    @GET("random")
    suspend fun getRandomQuote(): QuoteResponse
}

class ApiConfig {
    companion object {
        private const val baseUrl = "https://api.quotable.io/"
        private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        fun getApiService() = retrofit.create(QuoteApi::class.java)
    }
}
