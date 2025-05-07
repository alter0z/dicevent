package com.ansorisan.dicevent.features.events.detail.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ansorisan.dicevent.base.data.models.BaseDataResponse
import com.ansorisan.dicevent.base.data.models.UiState
import com.ansorisan.dicevent.base.domain.entities.Event
import com.ansorisan.dicevent.base.domain.usecases.GetEventDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getEventDetailUseCase: GetEventDetailUseCase,
) : ViewModel() {
    private val _eventsState = MutableStateFlow<UiState<BaseDataResponse<Event>>>(UiState.Loading)
    val eventsState: StateFlow<UiState<BaseDataResponse<Event>>> get() = _eventsState

    fun fetchDetail(id: Int) {
        viewModelScope.launch {
            _eventsState.value = UiState.Loading
            try {
                val response = getEventDetailUseCase(id)
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