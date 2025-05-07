package com.ansorisan.dicevent.base.data.models

import com.google.gson.annotations.SerializedName

data class BaseDataListResponse<T>(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("listEvents") val data: T
)
