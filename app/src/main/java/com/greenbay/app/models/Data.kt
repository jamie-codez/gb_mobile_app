package com.greenbay.app.models

import com.google.gson.annotations.SerializedName
import com.greenbay.app.ui.home.models.House

class Comm(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val type: String,
    val date: String,
    val time: String,
    val location: String,
    val status: String,
    val createdBy: String,
    val createdAt: String,
    val updatedAt: String
)

data class Communication(
    @SerializedName("to") val to: String,
    @SerializedName("from") val from: String? = null,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("dateCreated") val dateCreated: Long,
)

data class CommunicationResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: Communication
)

data class CommunicationListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: List<Communication>
)

data class HouseListResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: List<House>
)

data class HouseResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: House
)
