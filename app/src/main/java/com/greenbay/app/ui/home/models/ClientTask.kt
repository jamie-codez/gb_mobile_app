package com.greenbay.app.ui.home.models

import com.google.gson.annotations.SerializedName

data class ClientTask(
    @SerializedName("clientEmail")val clientEmail: String,
    @SerializedName("clientFirstName")val clientFirstName: String,
    @SerializedName("clientLastName")val clientLastName: String,
    @SerializedName("clientPhoneNumber")val clientPhoneNumber: String,
    @SerializedName("clientEmail")val createdByEmail: String,
    @SerializedName("createdByFirstName")val createdByFirstName: String,
    @SerializedName("createByLastName")val createdByLastName: String,
    @SerializedName("createOn")val createdOn: Long,
    @SerializedName("description")val description: String,
    @SerializedName("scheduleDate")val scheduleDate: String,
    @SerializedName("status")val status: String,
    @SerializedName("title")val title: String
)