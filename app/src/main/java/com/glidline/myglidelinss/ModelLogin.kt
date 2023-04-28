package com.glidline.myglidelinss


import com.google.gson.annotations.SerializedName

data class ModelLogin(
    @SerializedName("message")
    val message: String,
    @SerializedName("profile")
    val profile: Profile,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String
)