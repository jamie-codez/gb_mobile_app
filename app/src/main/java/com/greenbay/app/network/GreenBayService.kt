package com.greenbay.app.network

import com.greenbay.app.models.AppUser
import com.greenbay.app.models.LoginModel
import com.greenbay.app.models.ResponseModel
import com.greenbay.app.ui.home.models.Communication
import com.greenbay.app.ui.home.models.CommunicationUpdate
import com.greenbay.app.ui.home.models.Payment
import com.greenbay.app.ui.home.models.PaymentUpdate
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GreenBayService {
    //User Endpoints
    @POST("/login")
    suspend fun login(@Body loginModel: LoginModel): ResponseModel

    @GET("/user/{id}")
    suspend fun getUser(): ResponseModel

    @PUT("/user/{id}")
    suspend fun updateUser(
        @Header("access-token") token: String,
        @Path("id") id: String,
        @Body appUser: AppUser
    ): ResponseModel

    //Team Endpoints

    @GET("/tenant/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getTenant(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): ResponseModel

    // Task Endpoints
    @GET("/task/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getTask(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): ResponseModel

    @GET("/tasks/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getTasks(
        @Header("access-token") token: String,
        @Path("page") page: Int
    ): ResponseModel

    //STK Push Endpoints
    @GET("/stk-push/")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getStkPush(
        @Header("access-token") token: String,amount:Int
    ): ResponseModel

    // Payments Endpoints
    @GET("/payments/{email}/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getPayments(
        @Header("access-token") token: String,
        @Path("email") email: String,
        @Path("page") page: Int
    ): ResponseModel

    @POST("/payments")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun createPayment(
        @Header("access-token") token: String,
        @Body payment: Payment
    ): ResponseModel

    @PUT("/payments/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun updatePayment(
        @Header("access-token") token: String,
        @Path("id") id: String,
        @Body payment: PaymentUpdate
    ): ResponseModel

    @GET("/payments/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getPayment(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): ResponseModel

    // House Endpoints

    @GET("/house/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getHouse(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): ResponseModel

    // Communication Endpoints
    @POST("/communications")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun createCommunication(
        @Header("access-token") token: String,
        @Body communication: Communication
    ): ResponseModel

    @GET("/communications/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getCommunications(
        @Header("access-token") token: String,
        @Path("page") page: Int
    ): ResponseModel

    @GET("/communications/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getCommunication(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): ResponseModel

    @PUT("/communications/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun updateCommunication(
        @Header("access-token") token: String,
        @Path("id") id: String,
        @Body communicationUpdate: CommunicationUpdate
    ): ResponseModel

    @GET("houses/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun getHouses(
        @Header("access-token") token: String,
        @Path("page") page: Int
    ): ResponseModel


}