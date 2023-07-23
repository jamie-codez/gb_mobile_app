package com.greenbay.app.models

import com.google.gson.annotations.SerializedName

data class Roles(
    @SerializedName("user") val user: Boolean,
    @SerializedName("admin") val admin: Boolean,
    @SerializedName("manager") val manage: Boolean
)