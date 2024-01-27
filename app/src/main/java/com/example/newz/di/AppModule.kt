package com.example.newz.di

import android.app.Application
import androidx.room.Room
import com.example.newz.data.local.NewsDao
import com.example.newz.data.local.NewsDatabase
import com.example.newz.data.local.NewsTypeConvertor
import com.example.newz.data.manager.LocalUserManagerImpl
import com.example.newz.data.remote.NewsApi
import com.example.newz.data.repository.NewsRepositoryImpl
import com.example.newz.domain.manager.LocalUserManager
import com.example.newz.domain.repository.NewsRepository
import com.example.newz.domain.usecases.app_entry.AppEntryUseCases
import com.example.newz.domain.usecases.app_entry.ReadAppEntry
import com.example.newz.domain.usecases.app_entry.SaveAppEntry
import com.example.newz.domain.usecases.news.DeleteArticles
import com.example.newz.domain.usecases.news.GetNews
import com.example.newz.domain.usecases.news.NewsUseCases
import com.example.newz.domain.usecases.news.SearchNews
import com.example.newz.domain.usecases.news.SelectArticle
import com.example.newz.domain.usecases.news.SelectArticles
import com.example.newz.domain.usecases.news.UpsertArticles
import com.example.newz.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager {
        return LocalUserManagerImpl(
            application
        )
    }
    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(
            localUserManager = localUserManager
        ),
        saveAppEntry = SaveAppEntry(
            localUserManager = localUserManager
        )
    )
    @Provides
    @Singleton
    fun provideNewsApi(
    ): NewsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)
    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ):NewsRepository = NewsRepositoryImpl(newsApi, newsDao)
    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
    ) :NewsUseCases= NewsUseCases(
        getNews = GetNews(
            newsRepository = newsRepository
        ),
        searchNews = SearchNews(
            newsRepository = newsRepository
        ),
        upsertArticles = UpsertArticles(
            newsRepository
        ),
        deleteArticles = DeleteArticles(newsRepository),
        selectArticles = SelectArticles(newsRepository),
        selectArticle = SelectArticle(newsRepository)
    )
    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java,
            "news_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ):NewsDao = newsDatabase.newsDao
}