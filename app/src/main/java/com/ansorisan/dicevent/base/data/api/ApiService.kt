package com.ansorisan.dicevent.base.data.api

import com.ansorisan.dicevent.base.data.models.BaseDataListResponse
import com.ansorisan.dicevent.base.data.models.BaseDataResponse
import com.ansorisan.dicevent.base.data.models.Event
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/events")
    suspend fun getEvents(@Query("active") active: Int): BaseDataListResponse<List<Event>>
    @GET("/events/{id}")
    suspend fun getEventDetail(@Path("id") id: Int): BaseDataResponse<Event>
    @GET("/events")
    suspend fun searchEvent(
        @Query("active") active: Int,
        @Query("q") keyword: String): BaseDataListResponse<List<Event>>
}