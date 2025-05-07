package com.ansorisan.dicevent.base.data.models

sealed class UiState<out R> private constructor() {
    data class Success<out T>(val data: T): UiState<T>()
    data class Error(val message: String): UiState<Nothing>()
    data object Loading: UiState<Nothing>()
}
