package com.greenbay.app.models

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class ResetBody(
    @SerializedName("email") val email: String
)

data class ResponseModel(
    @SerializedName("code") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: Any?
){
    constructor():this(0,"",null)
}

data class STKPayload(
    @SerializedName("amount")val amount:Int,
    @SerializedName("phone")val phone:String?=null,
)

data class LoginResponse(
    @SerializedName("code")val status: Int,
    @SerializedName("message")val message: String,
    @SerializedName("payload")val payload: LoginPayload
)

data class LoginPayload(
    @SerializedName("accessToken")val accessToken: String,
    @SerializedName("refreshToken")val refreshToken: String
)
