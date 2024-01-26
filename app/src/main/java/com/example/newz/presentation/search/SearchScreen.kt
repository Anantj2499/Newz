package com.example.newz.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newz.domain.model.Article
import com.example.newz.presentation.common.ArticlesList
import com.example.newz.presentation.common.SearchBar

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
         onSearch = {onEvent(SearchEvent.SearchNews)}
     )
        Spacer(modifier = Modifier.height(24.dp))
        state.articles?.let { it ->
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(articles = articles, onClick = {navigateToDetails(it)})
        }
    }
}