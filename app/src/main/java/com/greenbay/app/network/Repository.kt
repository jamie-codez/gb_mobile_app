package com.greenbay.app.network

import com.greenbay.app.models.AppUser
import com.greenbay.app.models.LoginModel
import com.greenbay.app.ui.home.models.Communication
import com.greenbay.app.ui.home.models.CommunicationUpdate
import com.greenbay.app.ui.home.models.Payment
import com.greenbay.app.ui.home.models.PaymentUpdate

class Repository(private val apiService: GreenBayService) {
    suspend fun login(loginModel: LoginModel) = apiService.login(loginModel)
    suspend fun getUser() = apiService.getUser()
    suspend fun updateUser(token: String, id: String, appUser: AppUser) =
        apiService.updateUser(token, id, appUser)

    suspend fun getHouse(token: String, id: String) = apiService.getHouse(token, id)
    suspend fun getTenant(token: String, id: String) = apiService.getTenant(token, id)
    suspend fun getTask(token: String, id: String) = apiService.getTask(token, id)
    suspend fun getTasks(token: String, page: String) =
        apiService.getTasks(token, page.toInt())

    suspend fun getStkPush(token: String) = apiService.getStkPush(token)
    suspend fun getPayments(token: String, email: String, page: String) =
        apiService.getPayments(token, email, page.toInt())

    suspend fun getPayment(token: String, id: String) = apiService.getPayment(token, id)
    suspend fun createPayment(token: String, payment: Payment) =
        apiService.createPayment(token, payment)

    suspend fun updatePayment(token: String, id: String, paymentUpdate: PaymentUpdate) =
        apiService.updatePayment(token, id, paymentUpdate)

    suspend fun createCommunication(token: String, communication: Communication) =
        apiService.createCommunication(token, communication)

    suspend fun getCommunication(token: String, id: String) = apiService.getCommunication(token, id)
    suspend fun getCommunications(token: String, page: String) =
        apiService.getCommunications(token, page.toInt())

    suspend fun updateCommunication(
        token: String,
        id: String,
        communicationUpdate: CommunicationUpdate
    ) =
        apiService.updateCommunication(token, id, communicationUpdate)

    suspend fun getHouses(token: String, page: String) =
        apiService.getHouses(token, page.toInt())

}