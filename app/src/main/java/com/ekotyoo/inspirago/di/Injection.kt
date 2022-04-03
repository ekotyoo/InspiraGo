package com.ekotyoo.inspirago.di

import com.ekotyoo.inspirago.data.datasource.remote.ApiConfig
import com.ekotyoo.inspirago.data.datasource.remote.QuoteRemoteDataSource
import com.ekotyoo.inspirago.data.repositories.QuoteRepository

object Injection {
    fun provideRepository(): QuoteRepository {
        val quoteApi = ApiConfig.getApiService()
        val quoteRemoteDataSource = QuoteRemoteDataSource(quoteApi)
        return QuoteRepository(quoteRemoteDataSource)
    }
}