package com.ekotyoo.inspirago.di

import android.content.Context
import com.ekotyoo.inspirago.data.datasource.local.QuoteDatabase
import com.ekotyoo.inspirago.data.datasource.local.QuoteLocalDataSource
import com.ekotyoo.inspirago.data.datasource.remote.ApiConfig
import com.ekotyoo.inspirago.data.datasource.remote.QuoteRemoteDataSource
import com.ekotyoo.inspirago.data.repositories.QuoteRepository

object Injection {
    fun provideRepository(context: Context): QuoteRepository {
        val quoteApi = ApiConfig.getApiService()
        val quoteDatabase = QuoteDatabase.getDatabase(context)

        val quoteRemoteDataSource = QuoteRemoteDataSource(quoteApi)
        val quoteLocalDataSource = QuoteLocalDataSource(quoteDatabase.quoteDao())

        return QuoteRepository(quoteRemoteDataSource, quoteLocalDataSource)
    }
}