package com.ekotyoo.inspirago.data.datasource.local

import android.content.Context
import androidx.room.*
import com.ekotyoo.inspirago.data.entity.Quote
import kotlinx.coroutines.flow.Flow

@Database(entities = [Quote::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuoteDatabase::class.java,
                    "quote.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote")
    fun getAllQuote(): Flow<List<Quote>>

    @Insert
    suspend fun saveQuote(quote: Quote)

    @Query("DELETE FROM quote")
    suspend fun deleteAllQuote()
}