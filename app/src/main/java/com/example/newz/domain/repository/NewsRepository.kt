package com.example.newz.domain.repository

import androidx.paging.PagingData
import com.example.newz.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(source: List<String>, domain: List<String>):Flow<PagingData<Article>>
    fun searchNews(query: String, source: List<String>, domain: List<String>):Flow<PagingData<Article>>
    suspend fun upsertArticles(article: Article)
    suspend fun deleteArticles(article: Article)
    fun selectArticles(): Flow<List<Article>>
    suspend fun selectArticle(url: String): Article?

}