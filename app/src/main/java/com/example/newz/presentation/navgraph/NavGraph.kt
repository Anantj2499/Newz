package com.example.newz.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.newz.presentation.news_navigator.NewsNavigator
import com.example.newz.presentation.onboarding.OnBoardingScreen
import com.example.newz.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    startNavigation: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startNavigation) {
        navigation(
            route = Route.AppStartNavGraph.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }
        navigation(
            route = Route.NewsNavGraph.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(
                Route.NewsNavigatorScreen.route
            ) {
                NewsNavigator()
            }
        }
    }
}