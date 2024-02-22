package com.example.newz.domain.usecases.news

import com.example.newz.domain.model.SearchHistory
import com.example.newz.domain.repository.NewsRepository

class DeleteSearchHistory(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(searchHistory: SearchHistory){
        newsRepository.deleteSearchHistory(searchHistory)
    }
}