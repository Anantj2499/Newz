package com.example.newz.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newz.domain.model.SearchHistory
import com.example.newz.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel(){
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchNews -> {
                searchNews()
            }
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
            }
            is SearchEvent.RemoveSearchHistory -> {
                viewModelScope.launch {
                    val searchHistory = newsUseCases.selectSearch(event.searchQuery)
                    if (searchHistory != null) {
                        deleteSearchHistory(searchHistory)
                    }
                }
            }
            is SearchEvent.AddSearchHistory -> {
                viewModelScope.launch {
                    val searchHistory = SearchHistory(query = event.searchQuery)
                    upsertSearchHistory(searchHistory)
                }
            }

            else -> {}
        }
    }
    private suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        newsUseCases.deleteSearchHistory(searchHistory)
    }
    private suspend fun upsertSearchHistory(searchHistory: SearchHistory) {
        newsUseCases.upsertSearchHistory(searchHistory)
    }

    private fun searchNews() {
        val articles = newsUseCases.searchNews(
            searchQuery = state.value.searchQuery,
            source = listOf("bbc-news", "cnn", "fox-news", "google-news", "the-hindu", "the-times-of-india", "zee-news"),
            domain = listOf("ndtv.com", "hindustantimes.com", "indianexpress.com", "indiatoday.in", "indiatvnews.com", "moneycontrol.com", "screenrant.com", "india.com","abplive.com", "googleblog.in", "businessinsider.in", "in.ign.in")
        ).cachedIn(viewModelScope)
        _state.value = state.value.copy(articles = articles)
    }
    init {
        getSearchHistory()
    }
    private fun getSearchHistory() {
        newsUseCases.selectSearchHistory().onEach {
            _state.value = state.value.copy(searchHistory = it.asReversed())
        }.launchIn(viewModelScope)
    }
}