package com.greenbay.app.ui.auth.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.security.identity.AccessControlProfileId
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greenbay.app.R
import com.greenbay.app.models.AppUser
import com.greenbay.app.models.AppUserResponse
import com.greenbay.app.models.LoginModel
import com.greenbay.app.models.ResponseModel
import com.greenbay.app.network.Repository
import com.greenbay.app.network.RetrofitInstance
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(RetrofitInstance.getApiService())
    private var loginStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    private var appUser: MutableLiveData<AppUser> = MutableLiveData()
    private var prefs: SharedPreferences = application.getSharedPreferences(
        application.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )
    private var updateUser = MutableLiveData<AppUser>()

    fun login(email: String, password: String): LiveData<Boolean> {
        viewModelScope.launch {
            repository.login(LoginModel(email,password)).enqueue (object: Callback<ResponseModel> {
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        if (response.code() == 200) {
                            loginStatus = MutableLiveData(true)
                            response.body()?.let {
                                val accessToken =
                                    JSONObject(it.data.toString()).getString("accessToken")
                                with(prefs.edit()) {
                                    putString("accessToken", accessToken)
                                    apply()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        loginStatus = MutableLiveData(false)
                    }
                })
        }
        return loginStatus
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

    fun getUser(id: String): LiveData<AppUser> {
        val accessToken = prefs.getString("accessToken", "")
        if (!accessToken.isNullOrEmpty()) {
            viewModelScope.launch {
                repository.getUser(id).enqueue(object : Callback<AppUserResponse> {
                    override fun onResponse(
                        call: Call<AppUserResponse>,
                        response: Response<AppUserResponse>
                    ) {
                        if (response.code() == 200) {
                            val user = response.body()?.data as AppUser
                            appUser = MutableLiveData(user)
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
                            appUser = MutableLiveData()
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
            accessToken?.let {
                repository.updateUser(it, appUser.id, appUser)
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
        return updateUser
    }
}