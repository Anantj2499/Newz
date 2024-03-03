package com.example.newz.util

sealed class UIComponent {

    data class Toast(val message: String): UIComponent()
}