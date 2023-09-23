package com.greenbay.app.ui.auth.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greenbay.app.R
import com.greenbay.app.models.AppUser
import com.greenbay.app.models.AppUserResponse
import com.greenbay.app.models.LoginModel
import com.greenbay.app.models.LoginResponse
import com.greenbay.app.models.ResponseModel
import com.greenbay.app.network.Repository
import com.greenbay.app.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(RetrofitInstance.getApiService())

    //    private var loginResult: MutableLiveData<LoginResult> = MutableLiveData()
    private var prefs: SharedPreferences = application.getSharedPreferences(
        application.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )
    private var updateUser = MutableLiveData<AppUser>()
    var email: String = ""

    fun login(email: String, password: String): LiveData<LoginResult> {
        var loginResult: MutableLiveData<LoginResult> = MutableLiveData()
        viewModelScope.launch {
            repository.login(LoginModel(email, password)).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    loginResult = if (response.code() == 200) {
                        val loginResponse = response.body()
                        val accessToken = loginResponse?.payload?.accessToken
                        with(prefs.edit()) {
                            putString("accessToken", accessToken)
                            apply()
                        }
                        MutableLiveData(LoginResult(true, accessToken ?: ""))
                    } else {
                        MutableLiveData(LoginResult(false, ""))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginResult = MutableLiveData(LoginResult(false, ""))
                }
            })
        }
        return loginResult
    }

    fun logout() {
        with(prefs.edit()) {
            putString("accessToken", "")
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        val accessToken = prefs.getString("accessToken", "")
        return !accessToken.isNullOrEmpty()
    }

    fun getUser(accessToken: String?, email: String): LiveData<AppUser> {
        var appUser: MutableLiveData<AppUser> = MutableLiveData()
        if (!accessToken.isNullOrEmpty()) {
            viewModelScope.launch {
                repository.getUser(accessToken, email).enqueue(object : Callback<AppUserResponse> {
                    override fun onResponse(
                        call: Call<AppUserResponse>,
                        response: Response<AppUserResponse>
                    ) {
                        appUser = if (response.code() == 200) {
                            val user = response.body()?.payload
                            MutableLiveData(user)
                        } else {
                            MutableLiveData()
                        }
                    }

                    override fun onFailure(call: Call<AppUserResponse>, t: Throwable) {
                        appUser = MutableLiveData()
                    }
                })
            }
            return appUser
        } else {
            appUser = MutableLiveData()
            return appUser
        }
    }

    fun updateAppUser(activity: Activity, appUser: AppUser): LiveData<AppUser> {
        viewModelScope.launch {
            val accessToken = activity.getSharedPreferences(
                activity.getString(R.string.app_name),
                Context.MODE_PRIVATE
            ).getString("accessToken", "")
            val id = activity.getSharedPreferences(
                activity.getString(R.string.app_name),
                Context.MODE_PRIVATE
            ).getString("userId", "")
            accessToken?.let {
                if (id != null) {
                    repository.updateUser(it, id, appUser)
                        .enqueue(object : Callback<ResponseModel> {
                            override fun onResponse(
                                call: Call<ResponseModel>,
                                response: Response<ResponseModel>
                            ) {
                                if (response.code() == 200) {
                                    val user = response.body()?.data as AppUser
                                    updateUser = MutableLiveData(user)
                                    with(prefs.edit()) {
                                        putString("userId", user.id)
                                        putString("username", user.username)
                                        putString("firstName", user.firstName)
                                        putString("lastName", user.lastName)
                                        putString("email", user.email)
                                        putString("phone", user.phone)
                                        putString("idNumber", user.idNumber)
                                        putString("profileImage", user.profileImage)
                                        putString("roles", user.roles.toString())
                                        putString("password", user.password)
                                        putBoolean("verified", user.verified)
                                        apply()
                                    }
                                } else {
                                    updateUser = MutableLiveData()
                                }
                            }

                            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                                updateUser = MutableLiveData()
                            }
                        })
                }
            }
        }
        return updateUser
    }

    fun resetPassword(email: String): LiveData<ResponseModel> {
        val responseModel: MutableLiveData<ResponseModel> = MutableLiveData()
        viewModelScope.launch {
            repository.resetPassword(email).enqueue(object : Callback<ResponseModel>{
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    if (response.code() == 200){
                        val result = response.body()
                        responseModel.value = result
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    responseModel.value = ResponseModel(0,"",null)
                }
            })
        }
        return responseModel
    }
}


data class LoginResult(val success: Boolean, val accessToken: String)