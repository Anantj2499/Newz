package com.example.newz.presentation.home

import androidx.paging.PagingData
import com.example.newz.domain.model.Article
import kotlinx.coroutines.flow.Flow


data class HomeState(
    val scrollValue: Int = 0,
    val maxScrollValue: Int = 0,
    val articles: Flow<PagingData<Article>>? = null,
)
