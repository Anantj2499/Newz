package com.example.newz.presentation.navgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen: Route("onboardingScreen")
    object HomeScreen: Route("homeScreen")
    object SearchScreen: Route("searchScreen")
    object BookmarksScreen: Route("bookmarksScreen")
    object ArticleDetailScreen: Route("articleDetailScreen")
    object AppStartNavGraph: Route("appStartNavGraph")
    object NewsNavGraph: Route("newsNavGraph")
    object NewsNavigatorScreen: Route("newsNavigatorScreen")
}
