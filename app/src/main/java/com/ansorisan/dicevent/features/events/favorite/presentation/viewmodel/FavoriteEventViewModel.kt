package com.ansorisan.dicevent.features.events.favorite.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ansorisan.dicevent.base.data.models.UiState
import com.ansorisan.dicevent.features.events.favorite.domain.entity.Event
import com.ansorisan.dicevent.features.events.favorite.domain.usecase.DeleteEventsUseCase
import com.ansorisan.dicevent.features.events.favorite.domain.usecase.GetFavoriteEventsUseCase
import com.ansorisan.dicevent.features.events.favorite.domain.usecase.InsertEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteEventViewModel @Inject constructor(
    private val getFavoriteEventsUseCase: GetFavoriteEventsUseCase,
    private val insertEventsUseCase: InsertEventsUseCase,
    private val deleteEventsUseCase: DeleteEventsUseCase,
) : ViewModel() {
    private val _eventsState = MutableStateFlow<UiState<List<Event>>>(UiState.Loading)
    val eventsState: StateFlow<UiState<List<Event>>> get() = _eventsState

    private val _eventState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val eventState: StateFlow<UiState<String>> get() = _eventState

    fun fetchFavoriteEvents() {
        viewModelScope.launch {
            _eventsState.value = UiState.Loading
            try {
                val response = getFavoriteEventsUseCase()
                _eventsState.value = UiState.Success(response)
            } catch (e: Exception) {
                _eventsState.value = UiState.Error(e.toString())
            }
        }
    }

    fun addFavoriteEvent(event: Event) {
        viewModelScope.launch {
            _eventState.value = UiState.Loading
            try {
                insertEventsUseCase(event)
                _eventState.value = UiState.Success("Add to favorite successfully")
            } catch (e: Exception) {
                _eventState.value = UiState.Error(e.toString())
            }
        }
    }

    fun deleteFavoriteEvent(event: Event) {
        viewModelScope.launch {
            _eventState.value = UiState.Loading
            try {
                deleteEventsUseCase(event)
                _eventState.value = UiState.Success("Deleted from favorite")
            } catch (e: Exception) {
                _eventState.value = UiState.Error(e.toString())
            }
        }
    }
}