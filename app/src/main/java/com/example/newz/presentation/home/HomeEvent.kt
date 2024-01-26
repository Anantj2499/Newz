package com.example.newz.presentation.home

sealed class HomeEvent {
    data class UpdateScrollValue(val value: Int): HomeEvent()
    data class UpdateMaxScrollValue(val value: Int): HomeEvent()
}