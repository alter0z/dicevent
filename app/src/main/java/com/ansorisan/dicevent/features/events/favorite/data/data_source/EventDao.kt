package com.ansorisan.dicevent.features.events.favorite.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ansorisan.dicevent.base.data.models.BaseDataListResponse
import com.ansorisan.dicevent.features.events.favorite.data.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    suspend fun getAll(): List<Event>

//    @Query("SELECT * FROM events WHERE id = :id")
//    suspend fun getById(id: Int): Event?

    @Insert
    suspend fun insert(note: Event)

//    @Update
//    suspend fun update(note: Event)

    @Delete
    suspend fun delete(note: Event)
}