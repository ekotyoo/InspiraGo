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
                    "quote_db"
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

    @Query("SELECT * FROM quote WHERE is_bookmarked = 0 LIMIT 1")
    fun getQuoteToDisplay(): Flow<Quote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuote(quote: Quote)

    @Query("DELETE FROM quote WHERE is_bookmarked == 1 AND author = :author AND content = :content")
    suspend fun deleteQuote(author: String, content: String)
}