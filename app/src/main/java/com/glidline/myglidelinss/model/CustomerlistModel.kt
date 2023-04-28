package com.glidline.myglidelinss.model

data class CustomerlistModel(
    val status: Boolean,
    val users: ArrayList<User>
)

data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val user_name: String,
    val user_id: String,
    val mobile_number: String,
    val street_address: String,
    val street_address_2: String,
    val city: String,
    val country: String,
    val state: String,
    val latitude: String,
    val longitude: String,
    val zip_code: String,
    val device_id: String,
    val company_name: String,
    val profile_image: String,
    val role: String,
    val permissions: Permissions,
    val last_login: String,
    val status: Int,
    val created_at: String
)

data class Permissions(
    val quotes_create: Boolean,
    val quotes_read: Boolean,
    val quotes_update: Boolean,
    val quotes_delete: Boolean,
    val orders_create: Boolean,
    val orders_read: Boolean,
    val orders_update: Boolean,
    val orders_delete: Boolean,
    val invoices_read: Boolean,
    val surveys_read: Boolean,
    val surveys_update: Boolean,
    val drawings_read: Boolean,
    val drawings_update: Boolean,
    val installations_read: Boolean,
    val installations_update: Boolean,
    val services_create: Boolean,
    val services_read: Boolean,
    val services_update: Boolean,
    val services_delete: Boolean,
    val calendar_read: Boolean,
    val products_read: Boolean,
    val profile_read: Boolean,
    val profile_update: Boolean,
    val logo_read: Boolean
)
