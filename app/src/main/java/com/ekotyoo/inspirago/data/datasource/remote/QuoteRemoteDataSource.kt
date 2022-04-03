package com.ekotyoo.inspirago.data.datasource.remote

import com.ekotyoo.inspirago.data.QuoteResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class QuoteRemoteDataSource(
    private val quoteApi: QuoteApi,
) {
    suspend fun getRandomQuote() = quoteApi.getRandomQuote()
}

interface QuoteApi {
    @GET("random?maxLength=100")
    suspend fun getRandomQuote(): Response<QuoteResponse>
}

class ApiConfig {
    companion object {
        private const val baseUrl = "https://api.quotable.io/"
        private val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getApiService(): QuoteApi = retrofit.create(QuoteApi::class.java)
    }
}
