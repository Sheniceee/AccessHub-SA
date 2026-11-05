package com.example.accesshubsa.data.local

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val database = AccessHubDatabase.getDatabase(applicationContext)
            val dao = database.requestDao()
            val unsynced = dao.getUnsyncedRequests()

            Log.d("SyncWorker", "Found ${unsynced.size} to sync")

            for (req in unsynced) {
                try {
                    // TODO: Call your API here: api.createRequest(req)
                    // For demo, just mark as synced
                    Log.d("SyncWorker", "Syncing id=${req.id}")
                    dao.markAsSynced(req.id)
                } catch (e: Exception) {
                    Log.e("SyncWorker", "Failed id=${req.id}")
                }
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Sync error: ${e.message}")
            Result.retry()
        }
    }
}