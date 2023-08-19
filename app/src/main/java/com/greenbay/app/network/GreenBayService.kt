package com.greenbay.app.network

import com.greenbay.app.models.AppUser
import com.greenbay.app.models.AppUserResponse
import com.greenbay.app.models.CommunicationListResponse
import com.greenbay.app.models.HouseListResponse
import com.greenbay.app.models.HouseResponse
import com.greenbay.app.models.LoginModel
import com.greenbay.app.models.ResponseModel
import com.greenbay.app.ui.home.models.Communication
import com.greenbay.app.ui.home.models.CommunicationResponse
import com.greenbay.app.ui.home.models.CommunicationUpdate
import com.greenbay.app.ui.home.models.Payment
import com.greenbay.app.ui.home.models.PaymentListResponse
import com.greenbay.app.ui.home.models.PaymentResponse
import com.greenbay.app.ui.home.models.PaymentUpdate
import com.greenbay.app.ui.home.models.TaskListResponse
import com.greenbay.app.ui.home.models.TaskResponse
import com.greenbay.app.ui.home.models.TenantResponse
import retrofit2.Call
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
    fun login(@Body loginModel: LoginModel): Call<ResponseModel>

    @GET("/user/{id}")
    fun getUser(): Call<AppUserResponse>

    @PUT("/user/{id}")
    fun updateUser(
        @Header("access-token") token: String,
        @Path("id") id: String,
        @Body appUser: AppUser
    ): Call<ResponseModel>

    //Team Endpoints

    @GET("/tenant/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getTenant(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): Call<TenantResponse>

    // Task Endpoints
    @GET("/task/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getTask(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): Call<TaskResponse>

    @GET("/tasks/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getTasks(
        @Header("access-token") token: String,
        @Path("page") page: Int
    ): Call<TaskListResponse>

    //STK Push Endpoints
    @POST("/stk-push/")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getStkPush(
        @Header("access-token") token: String, @Body amount: Int
    ): Call<ResponseModel>

    // Payments Endpoints
    @GET("/payments/{email}/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getPayments(
        @Header("access-token") token: String,
        @Path("email") email: String,
        @Path("page") page: Int
    ): Call<PaymentListResponse>

    @POST("/payments")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun createPayment(
        @Header("access-token") token: String,
        @Body payment: Payment
    ): Call<ResponseModel>

    @PUT("/payments/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun updatePayment(
        @Header("access-token") token: String,
        @Path("id") id: String,
        @Body payment: PaymentUpdate
    ): Call<ResponseModel>

    @GET("/payments/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getPayment(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): Call<PaymentResponse>

    // House Endpoints

    @GET("/house/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getHouse(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): Call<HouseResponse>

    // Communication Endpoints
    @POST("/communications")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun createCommunication(
        @Header("access-token") token: String,
        @Body communication: Communication
    ): Call<ResponseModel>

    @GET("/communications/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getCommunications(
        @Header("access-token") token: String,
        @Path("page") page: Int
    ): Call<com.greenbay.app.ui.home.models.CommunicationListResponse>

    @GET("/communications/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getCommunication(
        @Header("access-token") token: String,
        @Path("id") id: String
    ): Call<CommunicationResponse>

    @PUT("/communications/{id}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun updateCommunication(
        @Header("access-token") token: String,
        @Path("id") id: String,
        @Body communicationUpdate: CommunicationUpdate
    ): Call<ResponseModel>

    @GET("houses/{page}")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    fun getHouses(
        @Header("access-token") token: String,
        @Path("page") page: Int
    ): Call<HouseListResponse>


}