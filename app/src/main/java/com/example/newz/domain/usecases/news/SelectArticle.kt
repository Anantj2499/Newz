package com.example.newz.domain.usecases.news

import com.example.newz.data.local.NewsDao
import com.example.newz.domain.model.Article
import com.example.newz.domain.repository.NewsRepository

class SelectArticle(
    private val newsRepository: NewsRepository
) {
   suspend operator fun invoke(url: String):Article? {
        return newsRepository.selectArticle(url)
    }
}