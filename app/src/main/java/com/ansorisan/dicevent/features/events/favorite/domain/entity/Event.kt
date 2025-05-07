package com.ansorisan.dicevent.features.events.favorite.domain.entity

data class Event(
	val id: Int = 0,
	val summary: String,
	val mediaCover: String,
	val name: String,
	val beginTime: String,
)