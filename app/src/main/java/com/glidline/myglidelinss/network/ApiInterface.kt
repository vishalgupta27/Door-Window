package com.glidline.myglidelinss.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("register")

    fun register(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("mobile_number") phone_number: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String,
        @Field("role") role: Int,
        @Field("company_name") company_name: String,
    ): Call<Map<Any, Any>>

    @FormUrlEncoded
@POST("login")
fun login(

    @Field("email") email: String,
    @Field("password") password: String,
): Call<Map<Any, Any>>

    @GET("customer/list")
     fun getUsers(
        @Header("Authorization") token: String
     ): Call<Map<Any, Any>>
}
