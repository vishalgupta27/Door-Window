package com.glidline.myglidelinss.model

class ModelRegister(
    val message: String,
    val profile: Profile,
    val status: Boolean,
    val token: String
)

data class Profile(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_login: String,
    val last_name: String,
    val mobile_number: String,
    val status: Int,
    val user_id: String,
    val user_name: Any
)

data class Failed(
    val message: Message,
    val status: Boolean
)

data class Message(
    val confirm_password: List<String>,
    val email: List<String>,
    val first_name: List<String>,
    val last_name: List<String>,
    val password: List<String>,
    val mobile_number: List<String>

)
