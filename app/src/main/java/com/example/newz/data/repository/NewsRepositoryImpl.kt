package com.example.newz.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newz.data.local.NewsDao
import com.example.newz.data.remote.NewsApi
import com.example.newz.data.remote.NewsPagingSource
import com.example.newz.data.remote.SearchNewsPagingSource
import com.example.newz.domain.model.Article
import com.example.newz.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override fun getNews(source: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi,
                    source.joinToString(","))
            }
        ).flow
    }

    override fun searchNews(query: String, source: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsApi,
                    query,
                    source.joinToString(","))
            }
        ).flow
    }

    override suspend fun upsertArticles(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticles(article: Article) {
        newsDao.delete(article)
    }

    override fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    override suspend fun selectArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }

}