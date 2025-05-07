package com.ansorisan.dicevent.base.data.models

import com.google.gson.annotations.SerializedName

data class BaseDataResponse<T>(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("event") val data: T
)
