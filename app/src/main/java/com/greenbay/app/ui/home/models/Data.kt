package com.greenbay.app.ui.home.models

data class Data(
    val clientEmail: String,
    val clientFirstName: String,
    val clientLastName: String,
    val clientPhoneNumber: String,
    val createdByEmail: String,
    val createdByFirstName: String,
    val createdByLastName: String,
    val createdOn: Long,
    val description: String,
    val scheduleDate: String,
    val status: String,
    val title: String
)