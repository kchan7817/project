package com.cookandroid.kotlin_project

data class LoginResponse(
    val birthday: String,
    val email: String,
    val id: String,
    val name: String,
    val pw: String,
    val success: Boolean
)