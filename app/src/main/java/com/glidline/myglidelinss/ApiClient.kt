package com.glidline.myglidelinss

import com.glidline.myglidelinss.network.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        var BASE_URL = "https://doorwindow.balajeekabachpan.org/api/user/"
        //https://doorwindow.balajeekabachpan.org/api/user/customer/list

val client: ApiInterface
    get() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
//            .addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .build()
//                chain.proceed(request)
//            }
            .build())
        .build().create(ApiInterface::class.java)
    }





}