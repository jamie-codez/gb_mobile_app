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
import com.greenbay.app.models.LoginModel
import com.greenbay.app.network.Repository
import com.greenbay.app.network.RetrofitInstance
import kotlinx.coroutines.launch

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
            val response = repository.login(LoginModel(email, password))
            if (response.status == 200) {
                loginStatus = MutableLiveData(true)
                with(prefs.edit()) {
                    putString("accessToken", response.data.toString())
                    apply()
                }
            }
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

    fun getUser(): LiveData<AppUser> {
        val accessToken = prefs.getString("accessToken", "")
        if (!accessToken.isNullOrEmpty()) {
            viewModelScope.launch {
                val response = repository.getUser()
                if (response.status == 200) {
                    val user = response.data as AppUser
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
            val response = accessToken?.let { repository.updateUser(it, appUser.id, appUser) }
            updateUser = if (response?.status == 200) {
                val user = response.data as AppUser
                MutableLiveData(user)
            } else {
                MutableLiveData()
            }
        }
        return updateUser
    }


}