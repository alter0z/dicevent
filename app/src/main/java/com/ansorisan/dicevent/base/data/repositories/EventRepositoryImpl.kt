package com.ansorisan.dicevent.base.data.repositories

import com.ansorisan.dicevent.base.data.api.ApiService
import com.ansorisan.dicevent.base.data.mapper.toEntity
import com.ansorisan.dicevent.base.data.models.BaseDataListResponse
import com.ansorisan.dicevent.base.data.models.BaseDataResponse
import com.ansorisan.dicevent.base.domain.entities.Event
import com.ansorisan.dicevent.base.domain.repositories.Event as Repo
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: ApiService
) : Repo {
    override suspend fun getEvents(active: Int): BaseDataListResponse<List<Event>> {
        val response = api.getEvents(active)
        return BaseDataListResponse(
            status = response.status,
            message = response.message,
            data = response.data.map { it.toEntity() }
        )
    }

    override suspend fun searchEvent(keyword: String): BaseDataListResponse<List<Event>> {
        val response = api.searchEvent(-1, keyword)
        return BaseDataListResponse(
            status = response.status,
            message = response.message,
            data = response.data.map { it.toEntity() }
        )
    }

    override suspend fun getEventDetail(id: Int): BaseDataResponse<Event> {
        val response = api.getEventDetail(id)
        return BaseDataResponse(
            status = response.status,
            message = response.message,
            data = response.data.toEntity()
        )
    }
}