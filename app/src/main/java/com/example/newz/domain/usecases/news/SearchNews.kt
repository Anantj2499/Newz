package com.example.newz.domain.usecases.news

import com.example.newz.domain.repository.NewsRepository

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String,source: List<String>, domain: List<String>)
    = newsRepository.searchNews(searchQuery,source, domain)
}