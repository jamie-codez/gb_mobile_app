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
    @SerializedName("to") val to: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
)

data class CommunicationUpdate(
    @SerializedName("to") val to: String?,
    @SerializedName("payload") val payload: Any?,
)