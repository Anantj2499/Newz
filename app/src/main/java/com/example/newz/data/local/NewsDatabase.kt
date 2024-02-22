package com.example.newz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newz.domain.model.Article
import com.example.newz.domain.model.SearchHistory

@Database(
    entities = [Article::class, SearchHistory::class],
    version = 2,
)
@TypeConverters(NewsTypeConvertor::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract val newsDao: NewsDao
    abstract val searchHistoryDao: SearchHistoryDao
}