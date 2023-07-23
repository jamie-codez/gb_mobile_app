package com.greenbay.app.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private lateinit var retrofit: Retrofit
        private const val baseUrl = ""

        @JvmStatic
        fun getRetrofitInstance(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()
            }
            return retrofit

        }

        @JvmStatic
        private fun getOkHttpClient() =
            OkHttpClient.Builder().apply {
                callTimeout(30, TimeUnit.SECONDS)
                readTimeout(20, TimeUnit.SECONDS)
                writeTimeout(40, TimeUnit.SECONDS)
                pingInterval(120, TimeUnit.SECONDS)
                connectTimeout(40, TimeUnit.SECONDS)
                addInterceptor(getLoggingInterceptor())
            }.build()

        @JvmStatic
        private fun getLoggingInterceptor() =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                level = HttpLoggingInterceptor.Level.HEADERS
                level = HttpLoggingInterceptor.Level.BODY
            }


        @JvmStatic
        fun getApiService(): GreenBayService =
            getRetrofitInstance().create(GreenBayService::class.java)
    }

}