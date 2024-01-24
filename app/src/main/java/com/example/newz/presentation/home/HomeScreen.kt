package com.example.newz.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.newz.R
import com.example.newz.domain.model.Article
import com.example.newz.presentation.common.ArticlesList
import com.example.newz.presentation.common.SearchBar
import com.example.newz.presentation.navgraph.Route

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails:(Article)->Unit
){
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount>10){
                articles.itemSnapshotList.items
                    .slice(0..9)
                    .joinToString ( separator = "\uD83d\uDFE5" ) { it.title }
            }else{
                ""
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .statusBarsPadding()){
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier =Modifier.height(24.dp))
        SearchBar(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = {},
            onClick = {
                navigateToSearch()
            }
        )
        Spacer(modifier =Modifier.height(24.dp))
        Text(
            text =titles,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )
        Spacer(modifier = Modifier.height(24.dp))
        ArticlesList(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            articles = articles,
            onClick = {
            navigateToDetails(it)
        })
    }
}