package com.example.newz.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newz.domain.model.Article
import com.example.newz.domain.usecases.news.NewsUseCases
import com.example.newz.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
):ViewModel() {
    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticles(article)
        sideEffect = UIComponent.Toast("Article saved successfully")
    }


    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticles(article)
        sideEffect = UIComponent.Toast("Article removed successfully")
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    if (article == null) {
                        upsertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }
}