package com.example.newz.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String) : SearchEvent()
    object SearchNews : SearchEvent()
    data class RemoveSearchHistory(val searchQuery: String) : SearchEvent()
    data class AddSearchHistory(val searchQuery: String) : SearchEvent()

}