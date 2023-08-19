package com.greenbay.app.ui.home.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greenbay.app.models.HouseListResponse
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(RetrofitInstance.getApiService())
    private val pref = application.getSharedPreferences(
        application.getString(com.greenbay.app.R.string.app_name),
        Context.MODE_PRIVATE
    )
    private val accessToken = pref.getString("accessToken", "")!!
    val email = pref.getString("email", "")!!

    fun getCommunications(): MutableLiveData<List<Communication>> {
        val communications = MutableLiveData<List<Communication>>()
        viewModelScope.launch {
            repository.getCommunications(accessToken, "1")
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            communications.value = response.body()?.data as List<Communication>
                        } else {
                            communications.value = listOf()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        communications.value = listOf()
                    }

                })
        }
        return communications
    }

    fun getCommunication(id: String): MutableLiveData<Communication> {
        val communication = MutableLiveData<Communication>()
        viewModelScope.launch {
            repository.getCommunication(accessToken, id)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            communication.value = response.body()?.data as Communication
                        } else {
                            communication.value = Communication("", "", "", "", "", "", 0)
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        communication.value = Communication("", "", "", "", "", "", 0)
                    }

                })
        }
        return communication
    }

    fun createCommunication(communication: Communication): MutableLiveData<Communication> {
        val comm = MutableLiveData<Communication>()
        viewModelScope.launch {
            repository.createCommunication(accessToken, communication)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            comm.value = response.body()?.data as Communication
                        } else {
                            comm.value = Communication("", "", "", "", "", "", 0)
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        comm.value = Communication("", "", "", "", "", "", 0)
                    }
                })
        }
        return comm
    }

    fun updateCommunication(
        id: String,
        communicationUpdate: CommunicationUpdate
    ): MutableLiveData<Communication> {
        val updateCommunication = MutableLiveData<Communication>()
        viewModelScope.launch {
            repository.updateCommunication(accessToken, id, communicationUpdate)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            updateCommunication.value = response.body()?.data as Communication
                        } else {
                            updateCommunication.value = Communication("", "", "", "", "", "", 0)
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        updateCommunication.value = Communication("", "", "", "", "", "", 0)
                    }
                })
        }
        return updateCommunication
    }

    fun getTasks(): MutableLiveData<List<Task>> {
        val tasks = MutableLiveData<List<Task>>()
        viewModelScope.launch {
            repository.getTasks(accessToken, "1").enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseModel>,
                    response: retrofit2.Response<ResponseModel>
                ) {
                    if (response.code() == 200) {
                        tasks.value = response.body()?.data as List<Task>
                    } else {
                        tasks.value = listOf()
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                    tasks.value = listOf()
                }
            })
        }
        return tasks
    }

    fun getTask(id: String): MutableLiveData<Task> {
        val task = MutableLiveData<Task>()
        viewModelScope.launch {
            repository.getTask(accessToken, id).enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseModel>,
                    response: retrofit2.Response<ResponseModel>
                ) {
                    if (response.code() == 200) {
                        task.value = response.body()?.data as Task
                    } else {
                        task.value = Task("", "", "", 0, 0, "", false)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                    task.value = Task("", "", "", 0, 0, "", false)
                }
            })
        }
        return task
    }

    fun getPayments(): MutableLiveData<List<Payment>> {
        val payments = MutableLiveData<List<Payment>>()
        viewModelScope.launch {
            repository.getPayments(accessToken, email, "1")
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            payments.value = response.body()?.data as List<Payment>
                        } else {
                            payments.value = listOf()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        payments.value = listOf()
                    }
                })
        }
        return payments
    }

    fun getPayment(id: String): MutableLiveData<Payment> {
        val payment = MutableLiveData<Payment>()
        viewModelScope.launch {

            repository.getPayment(accessToken, id).enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseModel>,
                    response: retrofit2.Response<ResponseModel>
                ) {
                    if (response.code() == 200) {
                        payment.value = response.body()?.data as Payment
                    } else {
                        payment.value = Payment("", "", "", "", "", "", 0, false)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                    payment.value = Payment("", "", "", "", "", "", 0, false)
                }
            })
        }
        return payment
    }

    fun getStkPush(amount: Int): MutableLiveData<ResponseModel> {
        val stkResponse = MutableLiveData<ResponseModel>()
        viewModelScope.launch {
            repository.getStkPush(accessToken, amount)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            stkResponse.value = response.body() as ResponseModel
                        } else {
                            stkResponse.value = ResponseModel(0, "", "")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        stkResponse.value = ResponseModel(0, "", "")
                    }
                })
        }
        return stkResponse
    }

    fun createPayment(payment: Payment): MutableLiveData<Payment> {
        val pay = MutableLiveData<Payment>()
        viewModelScope.launch {
            repository.createPayment(accessToken, payment)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            pay.value = response.body()?.data as Payment
                        } else {
                            pay.value = Payment("", "", "", "", "", "", 0, false)
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        pay.value = Payment("", "", "", "", "", "", 0, false)
                    }
                })
        }
        return pay
    }

    fun updatePayment(id: String, paymentUpdate: PaymentUpdate): MutableLiveData<Payment> {
        val updatePayment = MutableLiveData<Payment>()
        viewModelScope.launch {
            repository.updatePayment(accessToken, id, paymentUpdate)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseModel>,
                        response: retrofit2.Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            updatePayment.value = response.body()?.data as Payment
                        } else {
                            updatePayment.value = Payment("", "", "", "", "", "", 0, false)
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                        updatePayment.value = Payment("", "", "", "", "", "", 0, false)
                    }
                })
        }
        return updatePayment
    }

    fun getTenant(id: String): MutableLiveData<Tenant> {
        val tenant = MutableLiveData<Tenant>()
        viewModelScope.launch {

            repository.getTenant(accessToken, id).enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseModel>,
                    response: retrofit2.Response<ResponseModel>
                ) {
                    if (response.code() == 200) {
                        tenant.value = response.body()?.data as Tenant
                    } else {
                        tenant.value = Tenant("", "", "", "", "", "", "", "", "")
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                    tenant.value = Tenant("", "", "", "", "", "", "", "", "")
                }
            })
        }
        return tenant
    }

    fun getHouse(id: String): MutableLiveData<House> {
        val house = MutableLiveData<House>()
        viewModelScope.launch {

            repository.getHouse(accessToken, id).enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseModel>,
                    response: retrofit2.Response<ResponseModel>
                ) {
                    if (response.code() == 200) {
                        house.value = response.body()?.data as House
                    } else {
                        house.value = House("", "", "", "", "", "", "", false)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseModel>, t: Throwable) {
                    house.value = House("", "", "", "", "", "", "", false)
                }
            })
        }
        return house
    }

    fun getHouses(): MutableLiveData<List<House>?> {
        val houses = MutableLiveData<List<House>?>()
        viewModelScope.launch {

            repository.getHouses(accessToken, "1").enqueue(object : Callback<HouseListResponse> {
                override fun onResponse(
                    call: Call<HouseListResponse>,
                    response: Response<HouseListResponse>
                ) {
                    if (response.code() == 200) {
                        houses.value = response.body()?.data
                    } else {
                        houses.value = listOf()
                    }
                }

                override fun onFailure(call: Call<HouseListResponse>, t: Throwable) {
                    houses.value = listOf()
                }
            })
        }
        return houses
    }

}