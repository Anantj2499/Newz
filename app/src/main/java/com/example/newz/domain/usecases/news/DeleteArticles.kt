package com.example.newz.domain.usecases.news

import com.example.newz.domain.model.Article
import com.example.newz.domain.repository.NewsRepository

class DeleteArticles (
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article){
        newsRepository.deleteArticles(article)
    }
}