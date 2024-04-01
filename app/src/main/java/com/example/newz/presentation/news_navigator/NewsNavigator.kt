package com.example.newz.presentation.news_navigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newz.R
import com.example.newz.domain.model.Article
import com.example.newz.presentation.bookmark.BookmarkScreen
import com.example.newz.presentation.bookmark.BookmarkViewModel
import com.example.newz.presentation.details.DetailsScreen
import com.example.newz.presentation.details.DetailsViewModel
import com.example.newz.presentation.home.HomeScreen
import com.example.newz.presentation.home.HomeViewModel
import com.example.newz.presentation.navgraph.Route
import com.example.newz.presentation.news_navigator.components.BottomNavigationItem
import com.example.newz.presentation.news_navigator.components.NewsBottomNavigation
import com.example.newz.presentation.search.SearchScreen
import com.example.newz.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
         listOf(
            BottomNavigationItem(
                icon = R.drawable.ic_home,
                title = "Home",
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_search,
                title = "Search",
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_bookmark,
                title = "Bookmarks",
            ),
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when(backStackState?.destination?.route){
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarksScreen.route -> 2
        else -> 0
    }
    val isBottomBarVisible = remember(key1 = backStackState){
        backStackState?.destination?.route in listOf(
            Route.HomeScreen.route,
            Route.SearchScreen.route,
            Route.BookmarksScreen.route,
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateTo(navController, Route.HomeScreen.route)
                            1 -> navigateTo(navController, Route.SearchScreen.route)
                            2 -> navigateTo(navController, Route.BookmarksScreen.route)
                        }
                    }
                )
            }
        }
    ) {
        val bottomPaddingValues = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPaddingValues)
        ){
            composable(Route.HomeScreen.route){
                val viewModel: HomeViewModel = hiltViewModel()
                val state by viewModel.state
                HomeScreen(
                    navigateToSearch = { navigateTo(navController, Route.SearchScreen.route) },
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    },
                    state = state,
                    event = viewModel::onEvent
                )
            }
            composable(Route.SearchScreen.route){
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }
            composable(Route.ArticleDetailScreen.route){
                val viewModel: DetailsViewModel = hiltViewModel()
                val state = viewModel.state
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let {article ->
                    DetailsScreen(
                        state = state.value,
                        article = article,
                        event = viewModel::onEvent,
                        navigateUp = {navController.navigateUp()},
                        sideEffect = viewModel.sideEffect
                    )
                }
            }
            composable(Route.BookmarksScreen.route){
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController)
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateTo(navController, Route.HomeScreen.route)
    }
}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreenRoute ->
            popUpTo(homeScreenRoute) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}
private fun navigateToDetails(navController: NavController, article: Article){
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.ArticleDetailScreen.route)
}