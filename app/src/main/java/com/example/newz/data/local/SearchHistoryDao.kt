package com.example.newz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newz.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(searchHistory: SearchHistory)

    @Delete
    suspend fun delete(searchHistory: SearchHistory)

    @Query("SELECT * FROM SearchHistory")
    fun getSearchHistory(): Flow<List<SearchHistory>>

    @Query("SELECT * FROM SearchHistory WHERE `query` = :query")
    suspend fun getSearch(query: String): SearchHistory?
}