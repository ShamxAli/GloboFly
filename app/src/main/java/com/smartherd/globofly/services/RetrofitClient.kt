package com.smartherd.globofly.services

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val URL = "http://127.0.0.1:9000/"
    // private const val URL = "http://10.0.2.2:9000/"


    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    // 6.1 Custom Interceptors
    private val headerInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
                // x for user defined
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .build()

            val proceed = chain.proceed(request)
            return proceed
        }

    }


    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS) // by default retrofit have 10 seconds
        .addInterceptor(headerInterceptor)
        .addInterceptor(logger)

    // Create Retrofit Builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}