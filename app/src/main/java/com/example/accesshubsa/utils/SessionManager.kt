package com.example.accesshubsa.utils

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("accesshub_session", Context.MODE_PRIVATE)

    fun saveLogin(token: String, userId: Int, firstName: String, surname: String, email: String) {
        prefs.edit().putString("token", token).putInt("userId", userId)
            .putString("firstName", firstName).putString("surname", surname)
            .putString("email", email).apply()
    }

    fun getToken(): String? = prefs.getString("token", null)
    fun getUserId(): Int = prefs.getInt("userId", 0)
    fun clear() = prefs.edit().clear().apply()
    fun isLoggedIn(): Boolean = !getToken().isNullOrBlank()
}
