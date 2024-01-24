package com.example.newz.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newz.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {
    val news = newsUseCases.getNews(
        source = listOf("bbc-news", "cnn", "fox-news", "google-news", "the-hindu", "the-times-of-india", "zee-news", )
    ).cachedIn(viewModelScope)
}