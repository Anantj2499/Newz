package com.example.newz.presentation.bookmark

import com.example.newz.domain.model.Article

data class BookmarkState (
    val articles: List<Article> = emptyList()
)