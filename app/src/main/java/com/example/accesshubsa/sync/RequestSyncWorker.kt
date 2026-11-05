package com.example.accesshubsa.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.accesshubsa.data.local.AccessHubDatabase
import com.example.accesshubsa.data.remote.CreateRequest
import com.example.accesshubsa.data.remote.RetrofitClient

class RequestSyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val dao = AccessHubDatabase.getDatabase(applicationContext).requestDao()
        val pending = dao.getUnsyncedRequests()
        if (pending.isEmpty()) return Result.success()

        return try {
            val api = RetrofitClient.create(applicationContext)
            for (request in pending) {
                val response = api.createRequest(CreateRequest(request.serviceId, request.description, request.priority))
                if (response.isSuccessful) dao.markAsSynced(request.id)
                else return Result.retry()
            }
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
