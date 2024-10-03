package com.example.newz.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newz.domain.model.Article

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier, articles: List<Article>, onClick: (Article) -> Unit
) {
    if (articles.isEmpty()) {
        EmptyScreen()
        return
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(all = 6.dp)
    ) {
        items(articles.size) { index ->
            val article = articles[index]
            ArticleCard(
                article = article,
                onClick = { onClick(article) },
            )
        }
    }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier, articles: LazyPagingItems<Article>, onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(all = 6.dp)
        ) {
            items(articles.itemCount) { index ->
                articles[index]?.let { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onClick(article) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onRefresh: () -> Unit,
    onClick: (Article) -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        onRefresh = onRefresh,
        state = pullToRefreshState,
        isRefreshing = articles.loadState.refresh is LoadState.Loading,
        modifier = modifier.fillMaxSize()
    ) {
        val handlePagingResult = handlePagingResult(articles = articles)
        if (handlePagingResult) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(all = 6.dp),
                state = lazyListState
            ) {
                items(articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        ArticleCard(
                            article = article,
                            onClick = { onClick(article) },
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>,
): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> {
            loadState.refresh as LoadState.Error
        }

        loadState.append is LoadState.Error -> {
            loadState.append as LoadState.Error
        }

        loadState.prepend is LoadState.Error -> {
            loadState.prepend as LoadState.Error
        }

        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error)
            false
        }

        articles.itemCount == 0 -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }

    }
}

@Composable
private fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        repeat(10) {
            ArticleCardShimmerEffect(modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}
