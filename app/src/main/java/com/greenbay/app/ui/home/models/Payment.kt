package com.greenbay.app.ui.home.models

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("_id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("transactionCode") val transactionCode: String?,
    @SerializedName("amount") val amount: String?,
    @SerializedName("from") val from: String?,
    @SerializedName("dateCreated") val dateCreated: Long?,
    @SerializedName("verified") val verified: Boolean?,
)

data class PaymentUpdate(
    @SerializedName("from") val from: String?,
    @SerializedName("transactionCode") val transactionCode: String?,
    @SerializedName("payload") val payload: Any?,
)

data class Communication(
    @SerializedName("_id") val id: String?,
    @SerializedName("id") val commId: String?,
    @SerializedName("to") val to: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("dateCreated") val dateCreated: Long?,
)

data class CommunicationUpdate(
    @SerializedName("to") val to: String?,
    @SerializedName("payload") val payload: Any?,
)

data class Task(
    @SerializedName("_id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("scheduledDate") val scheduledDate: Long?,
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("createdOn") val createdOn: Long?,
    @SerializedName("status") val status: Boolean?,
)

data class Tenant(
    @SerializedName("_id") val id: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("houseNumber") val houseNumber: String?,
    @SerializedName("rent") val rent: String?,
    @SerializedName("deposit") val deposit: String?,
    @SerializedName("floorNumber") val floorNumber: String?,
)

data class House(
    @SerializedName("_id") val id: String?,
    @SerializedName("houseNumber") val houseNumber: String?,
    @SerializedName("rent") val rent: String?,
    @SerializedName("deposit") val deposit: String?,
    @SerializedName("floorNumber") val floorNumber: String?,
    @SerializedName("addedBy") val addedBy: String?,
    @SerializedName("createdOn") val createdOn: String?,
    @SerializedName("occupied") val occupied: Boolean?,
)