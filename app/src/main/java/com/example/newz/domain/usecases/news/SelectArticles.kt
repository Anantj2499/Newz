package com.example.newz.domain.usecases.news

import com.example.newz.data.local.NewsDao
import com.example.newz.domain.model.Article
import com.example.newz.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles (
    private val newsRepository: NewsRepository
) {
    operator fun invoke() : Flow<List<Article>>{
        return newsRepository.selectArticles()
    }
}