package com.example.newz.domain.usecases.news

import com.example.newz.domain.repository.NewsRepository

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(source: List<String>) = newsRepository.getNews(source)
}