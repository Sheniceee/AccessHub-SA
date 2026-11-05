package com.example.accesshubsa.data.local

import android.util.Log

class RequestRepository(
    private val database: AccessHubDatabase
) {

    suspend fun createRequest(serviceId: Int, description: String, priority: String) {
        val request = ServiceRequest(
            serviceId = serviceId,
            description = description,
            priority = priority,
            status = "Pending Sync",
            synced = false
        )

        try {
            database.requestDao().insert(request)
            Log.d("RequestRepository", "Saved locally: $description")
        } catch (e: Exception) {
            Log.e("RequestRepository", "Error: ${e.message}")
        }
    }

    fun getAllRequests() = database.requestDao().getAllRequests()
}