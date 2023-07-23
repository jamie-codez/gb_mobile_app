package com.greenbay.app.ui.home.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greenbay.app.models.ResponseModel
import com.greenbay.app.network.Repository
import com.greenbay.app.network.RetrofitInstance
import com.greenbay.app.ui.home.models.Communication
import com.greenbay.app.ui.home.models.CommunicationUpdate
import com.greenbay.app.ui.home.models.House
import com.greenbay.app.ui.home.models.Payment
import com.greenbay.app.ui.home.models.PaymentUpdate
import com.greenbay.app.ui.home.models.Task
import com.greenbay.app.ui.home.models.Tenant
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(RetrofitInstance.getApiService())
    private val pref = application.getSharedPreferences(
        application.getString(com.greenbay.app.R.string.app_name),
        Context.MODE_PRIVATE
    )
    private val accessToken = pref.getString("accessToken", "")!!
    private val email = pref.getString("email", "")!!
    private val communications = MutableLiveData<List<Communication>>()
    private val houses = MutableLiveData<List<House>>()
    private val communication = MutableLiveData<Communication>()
    private val updateCommunication = MutableLiveData<Communication>()
    private val tasks = MutableLiveData<List<Task>>()
    private val task = MutableLiveData<Task>()
    private val payments = MutableLiveData<List<Payment>>()
    private val payment = MutableLiveData<Payment>()
    private val updatePayment = MutableLiveData<Payment>()
    private val tenant = MutableLiveData<Tenant>()
    private val stkResponse = MutableLiveData<ResponseModel>()

    fun getCommunications(): MutableLiveData<List<Communication>> {
        viewModelScope.launch {
            val response = repository.getCommunications(accessToken, "1")
            if (response.status == 200) {
                communications.value = response.data as List<Communication>
            } else {
                communications.value = listOf()
            }
        }
        return communications
    }

    fun getCommunication(id: String): MutableLiveData<Communication> {
        viewModelScope.launch {
            val response = repository.getCommunication(accessToken, id)
            if (response.status == 200) {
                communication.value = response.data as Communication
            } else {
                communication.value = Communication("", "", "")
            }
        }
        return communication
    }

    fun createCommunication(communication: Communication): MutableLiveData<Communication> {
        viewModelScope.launch {
            val response = repository.createCommunication(accessToken, communication)
            if (response.status == 200) {
                updateCommunication.value = response.data as Communication
            } else {
                updateCommunication.value = Communication("", "", "")
            }
        }
        return updateCommunication
    }

    fun updateCommunication(
        id: String,
        communicationUpdate: CommunicationUpdate
    ): MutableLiveData<Communication> {
        viewModelScope.launch {
            val response = repository.updateCommunication(accessToken, id, communicationUpdate)
            if (response.status == 200) {
                updateCommunication.value = response.data as Communication
            } else {
                updateCommunication.value = Communication("", "", "")
            }
        }
        return updateCommunication
    }

    fun getTasks(): MutableLiveData<List<Task>> {
        viewModelScope.launch {
            val response = repository.getTasks(accessToken, "1")
            if (response.status == 200) {
                tasks.value = response.data as List<Task>
            } else {
                tasks.value = listOf()
            }
        }
        return tasks
    }

    fun getTask(id: String): MutableLiveData<Task> {
        viewModelScope.launch {
            val response = repository.getTask(accessToken, id)
            if (response.status == 200) {
                task.value = response.data as Task
            } else {
                task.value = Task("", "", "", 0, 0, "", 0, false)
            }
        }
        return task
    }

    fun getPayments(): MutableLiveData<List<Payment>> {
        viewModelScope.launch {
            val response = repository.getPayments(accessToken, email, "1")
            if (response.status == 200) {
                payments.value = response.data as List<Payment>
            } else {
                payments.value = listOf()
            }
        }
        return payments
    }

    fun getPayment(id: String): MutableLiveData<Payment> {
        viewModelScope.launch {
            val response = repository.getPayment(accessToken, id)
            if (response.status == 200) {
                payment.value = response.data as Payment
            } else {
                payment.value = Payment("", "", "", "", "", "", 0, false)
            }
        }
        return payment
    }

    fun getStkPush(): MutableLiveData<ResponseModel> {
        viewModelScope.launch {
            val response = repository.getStkPush(accessToken)
            if (response.status == 200) {
                stkResponse.value = response.data as ResponseModel
            } else {
                stkResponse.value = ResponseModel(0, "", "")
            }
        }
        return stkResponse
    }

    fun createPayment(payment: Payment): MutableLiveData<Payment> {
        viewModelScope.launch {
            val response = repository.createPayment(accessToken, payment)
            if (response.status == 200) {
                updatePayment.value = response.data as Payment
            } else {
                updatePayment.value = Payment("", "", "", "", "", "", 0, false)
            }
        }
        return updatePayment
    }

    fun updatePayment(id: String, paymentUpdate: PaymentUpdate): MutableLiveData<Payment> {
        viewModelScope.launch {
            val response = repository.updatePayment(accessToken, id, paymentUpdate)
            if (response.status == 200) {
                updatePayment.value = response.data as Payment
            } else {
                updatePayment.value = Payment("", "", "", "", "", "", 0, false)
            }
        }
        return updatePayment
    }

    fun getTenant(id: String): MutableLiveData<Tenant> {
        viewModelScope.launch {
            val response = repository.getTenant(accessToken, id)
            if (response.status == 200) {
                tenant.value = response.data as Tenant
            } else {
                tenant.value = Tenant("", "", "", "", "", "", "", "", "")
            }
        }
        return tenant
    }

    fun getHouse(id: String): MutableLiveData<Tenant> {
        viewModelScope.launch {
            val response = repository.getHouse(accessToken, id)
            if (response.status == 200) {
                tenant.value = response.data as Tenant
            } else {
                tenant.value = Tenant("", "", "", "", "", "", "", "", "")
            }
        }
        return tenant
    }

    fun getHouses(): MutableLiveData<List<House>> {
        viewModelScope.launch {
            val response = repository.getHouses(accessToken, "1")
            if (response.status == 200) {
                houses.value = response.data as List<House>
            } else {
                houses.value = listOf()
            }
        }
        return houses
    }

}