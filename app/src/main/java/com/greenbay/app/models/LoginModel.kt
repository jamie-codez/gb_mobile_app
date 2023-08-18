package com.greenbay.app.models

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class ResponseModel(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("payload") val data: Any?
){
    constructor():this(0,"",null)
}
