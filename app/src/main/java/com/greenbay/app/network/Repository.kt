package com.greenbay.app.network

import com.greenbay.app.models.AppUser
import com.greenbay.app.models.LoginModel
import com.greenbay.app.ui.home.models.Communication
import com.greenbay.app.ui.home.models.CommunicationUpdate
import com.greenbay.app.ui.home.models.Payment
import com.greenbay.app.ui.home.models.PaymentUpdate

class Repository(private val apiService: GreenBayService) {
    fun login(loginModel: LoginModel) = apiService.login(loginModel)
    fun getUser(id: String) = apiService.getUser(id)
    fun updateUser(token: String, id: String, appUser: AppUser) =
        apiService.updateUser(token, id, appUser)

    fun getHouse(token: String, id: String) = apiService.getHouse(token, id)
    fun getTenant(token: String, id: String) = apiService.getTenant(token, id)
    fun getTask(token: String, id: String) = apiService.getTask(token, id)
    fun getTasks(token: String, page: String) =
        apiService.getTasks(token, page.toInt())

    fun getStkPush(token: String, amount: Int) = apiService.getStkPush(token, amount)
    fun getPayments(token: String, email: String, page: String) =
        apiService.getPayments(token, email, page.toInt())

    fun getPayment(token: String, id: String) = apiService.getPayment(token, id)
    fun createPayment(token: String, payment: Payment) =
        apiService.createPayment(token, payment)

    fun updatePayment(token: String, id: String, paymentUpdate: PaymentUpdate) =
        apiService.updatePayment(token, id, paymentUpdate)

    fun createCommunication(token: String, communication: Communication) =
        apiService.createCommunication(token, communication)

    fun getCommunication(token: String, id: String) = apiService.getCommunication(token, id)
    fun getCommunications(token: String, page: String) =
        apiService.getCommunications(token, page.toInt())

    fun updateCommunication(
        token: String,
        id: String,
        communicationUpdate: CommunicationUpdate
    ) =
        apiService.updateCommunication(token, id, communicationUpdate)

    fun getHouses(token: String, page: String) =
        apiService.getHouses(token, page.toInt())

}