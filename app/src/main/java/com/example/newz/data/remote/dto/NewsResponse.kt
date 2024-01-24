package com.example.newz.data.remote.dto

import com.example.newz.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)