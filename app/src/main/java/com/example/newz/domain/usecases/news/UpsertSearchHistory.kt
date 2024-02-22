package com.example.newz.domain.usecases.news

import com.example.newz.domain.model.SearchHistory
import com.example.newz.domain.repository.NewsRepository

class UpsertSearchHistory(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(search: SearchHistory){
        newsRepository.upsertSearchHistory(search)
    }
}