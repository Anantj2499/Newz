package com.example.newz.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newz.domain.model.Article

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val sources: String,
): PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = newsApi.getNews(page, sources)
            totalNewsCount+=response.articles.size
            val articles = response.articles.distinctBy { it.title }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (totalNewsCount >= response.totalResults) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}