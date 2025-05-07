package com.ansorisan.dicevent.base.domain.entities

data class Event(
	val summary: String? = null,
	val mediaCover: String? = null,
	val registrants: Int = 0,
	val imageLogo: String? = null,
	val link: String? = null,
	val description: String? = null,
	val ownerName: String? = null,
	val cityName: String? = null,
	val quota: Int = 0,
	val name: String? = null,
	val id: Int = 0,
	val beginTime: String? = null,
	val endTime: String? = null,
	val category: String? = null
)