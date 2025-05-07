package com.ansorisan.dicevent.features.events.upcoming.presentation.viewmodel

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
class UpcomingViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
) : ViewModel() {

    private val _eventsState = MutableStateFlow<UiState<BaseDataListResponse<List<Event>>>>(UiState.Loading)
    val eventsState: StateFlow<UiState<BaseDataListResponse<List<Event>>>> get() = _eventsState

    fun fetchUpcomingEvents() {
        viewModelScope.launch {
            _eventsState.value = UiState.Loading
            try {
                val response = getEventsUseCase(1)
                _eventsState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val errorMessage = try {
                    e.response()?.errorBody()?.string()?.let {
                        JSONObject(it).get("message")
                    }
                } catch (e: JSONException) {
                    e.localizedMessage
                } as String
                _eventsState.value = UiState.Error(errorMessage)
            }
        }
    }
}