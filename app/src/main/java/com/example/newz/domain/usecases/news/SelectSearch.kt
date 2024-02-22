package com.example.newz.domain.usecases.news

import com.example.newz.domain.model.SearchHistory
import com.example.newz.domain.repository.NewsRepository

class SelectSearch(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(query: String): SearchHistory?{
        return newsRepository.selectSearch(query)
    }
}