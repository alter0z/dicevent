package com.ansorisan.dicevent.base.domain.repositories

import com.ansorisan.dicevent.base.data.models.BaseDataListResponse
import com.ansorisan.dicevent.base.data.models.BaseDataResponse
import com.ansorisan.dicevent.base.domain.entities.Event

interface Event {
    suspend fun getEvents(active: Int): BaseDataListResponse<List<Event>>
    suspend fun searchEvent(keyword: String): BaseDataListResponse<List<Event>>
    suspend fun getEventDetail(id: Int): BaseDataResponse<Event>
}