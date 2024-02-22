package com.example.newz.presentation.search

import androidx.paging.PagingData
import com.example.newz.domain.model.Article
import com.example.newz.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow


data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null,
    val searchHistory: List<SearchHistory> = emptyList()
)