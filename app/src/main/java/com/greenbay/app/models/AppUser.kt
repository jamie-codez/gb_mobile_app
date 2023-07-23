package com.greenbay.app.models

import com.google.gson.annotations.SerializedName

data class AppUser(
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("idNumber") val idNumber: String,
    @SerializedName("profileImage") val profileImage: String,
    @SerializedName("roles") val roles: Roles,
    @SerializedName("password") val password: String,
    @SerializedName("verified") val verified: Boolean
)

data class AppUserResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: AppUser
)

