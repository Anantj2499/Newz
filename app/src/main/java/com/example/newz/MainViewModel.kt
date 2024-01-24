package com.example.newz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newz.domain.usecases.app_entry.AppEntryUseCases
import com.example.newz.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
):ViewModel(){
     var splashCondition by mutableStateOf(true)
        private set
    var startDestination by mutableStateOf(Route.AppStartNavGraph.route)
        private set
    init {
        appEntryUseCases.readAppEntry().onEach {shouldStartFromHome->
            if (shouldStartFromHome) {
                startDestination = Route.NewsNavGraph.route
            } else {
                startDestination = Route.AppStartNavGraph.route
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}