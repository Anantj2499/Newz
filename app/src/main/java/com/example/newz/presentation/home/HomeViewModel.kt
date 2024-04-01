package com.example.newz.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newz.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state
//    val news = newsUseCases.getNews(
//        source = listOf("bbc-news", "cnn", "fox-news", "google-news", "the-hindu", "the-times-of-india", "zee-news" ),
//        domain = listOf("ndtv.com", "hindustantimes.com", "indianexpress.com", "indiatoday.in", "indiatvnews.com", "moneycontrol.com", "screenrant.com", "india.com","abplive.com", "googleblog.in", "businessinsider.in", "in.ign.com")
//    ).cachedIn(viewModelScope)
    private fun updateScrollValue(value: Int) {
        _state.value = _state.value.copy(scrollValue = value)
    }
    private fun updateMaxScrollValue(value: Int) {
        _state.value = _state.value.copy(maxScrollValue = value)
    }

    private fun getNews() {
        viewModelScope.launch {
            val articles = newsUseCases.getNews(
                source = listOf("bbc-news", "cnn", "fox-news", "google-news", "the-hindu", "the-times-of-india", "zee-news"),
                domain = listOf("ndtv.com", "hindustantimes.com", "indianexpress.com", "indiatoday.in", "indiatvnews.com", "moneycontrol.com", "screenrant.com", "india.com","abplive.com", "googleblog.in", "businessinsider.in", "in.ign.com")
            ).cachedIn(viewModelScope)
            _state.value = _state.value.copy(articles = articles)
        }
    }
    init {
        getNews()
    }
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateScrollValue -> {
                updateScrollValue(event.value)
            }
            is HomeEvent.UpdateMaxScrollValue -> {
                updateMaxScrollValue(event.value)
            }
            is HomeEvent.LoadNews -> {
                getNews()
            }
        }
    }
}