package com.ansorisan.dicevent.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ansorisan.dicevent.base.data.models.BaseDataListResponse
import com.ansorisan.dicevent.base.data.models.UiState
import com.ansorisan.dicevent.base.domain.entities.Event
import com.ansorisan.dicevent.base.domain.usecases.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
) : ViewModel() {

    // upcoming
    private val _upcomingEventsState = MutableStateFlow<UiState<BaseDataListResponse<List<Event>>>>(UiState.Loading)
    val upcomingEventsState: StateFlow<UiState<BaseDataListResponse<List<Event>>>> get() = _upcomingEventsState

    fun fetchUpcomingEvents() {
        viewModelScope.launch {
            _upcomingEventsState.value = UiState.Loading
            try {
                val response = getEventsUseCase(1)
                _upcomingEventsState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val errorMessage = try {
                    e.response()?.errorBody()?.string()?.let {
                        JSONObject(it).get("message")
                    }
                } catch (e: JSONException) {
                    e.localizedMessage
                } as String
                _upcomingEventsState.value = UiState.Error(errorMessage)
            }
        }
    }

    // finished
    private val _finishedEventsState = MutableStateFlow<UiState<BaseDataListResponse<List<Event>>>>(UiState.Loading)
    val finishedEventsState: StateFlow<UiState<BaseDataListResponse<List<Event>>>> get() = _finishedEventsState

    fun fetchFinishedEvents() {
        viewModelScope.launch {
            _finishedEventsState.value = UiState.Loading
            try {
                val response = getEventsUseCase(0)
                _finishedEventsState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val errorMessage = try {
                    e.response()?.errorBody()?.string()?.let {
                        JSONObject(it).get("message")
                    }
                } catch (e: JSONException) {
                    e.localizedMessage
                } as String
                _finishedEventsState.value = UiState.Error(errorMessage)
            }
        }
    }
}