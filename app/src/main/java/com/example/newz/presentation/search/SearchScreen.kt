package com.example.newz.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newz.domain.model.Article
import com.example.newz.presentation.common.ArticlesList
import com.example.newz.presentation.common.SearchBar
import com.example.newz.presentation.search.components.SearchLog

@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigateToDetails: (Article)->Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
     SearchBar(
         text = state.searchQuery,
         readOnly = false,
         onValueChange = {onEvent(SearchEvent.UpdateSearchQuery(it))},
         onSearch = {
                onEvent(SearchEvent.AddSearchHistory(state.searchQuery))
             onEvent(SearchEvent.SearchNews)
         }
     )
        Spacer(modifier = Modifier.height(24.dp))
        if (state.searchQuery.isEmpty()) {
            state.searchHistory.let {
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(it.size){ index ->
                        val searchHistory = it[index]
                        SearchLog(
                            modifier = Modifier.fillMaxWidth(),
                            query = searchHistory.query,
                            onTap = {
                                onEvent(SearchEvent.UpdateSearchQuery(searchHistory.query))
                                onEvent(SearchEvent.AddSearchHistory(searchHistory.query))
                                onEvent(SearchEvent.SearchNews)
                            },
                            onClear = {
                                onEvent(SearchEvent.RemoveSearchHistory(searchHistory.query))
                            }
                        )
                    }
                }
            }
        } else {
            state.articles?.let { it ->
                val articles = it.collectAsLazyPagingItems()
                ArticlesList(articles = articles, onClick = {navigateToDetails(it)})
            }
        }
    }
}