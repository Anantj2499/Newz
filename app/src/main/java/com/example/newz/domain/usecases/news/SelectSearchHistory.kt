package com.example.newz.domain.usecases.news

import com.example.newz.domain.model.SearchHistory
import com.example.newz.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectSearchHistory(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<SearchHistory>> {
        return newsRepository.selectSearchHistory()
    }
}