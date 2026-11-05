package com.example.accesshubsa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(request: ServiceRequest): Long

    @Query("SELECT * FROM service_requests ORDER BY id DESC")
    fun getAllRequests(): Flow<List<ServiceRequest>>

    @Query("SELECT * FROM service_requests WHERE synced = 0")
    suspend fun getUnsyncedRequests(): List<ServiceRequest>

    @Query("UPDATE service_requests SET synced = 1, status = 'Submitted' WHERE id = :id")
    suspend fun markAsSynced(id: Long)

    @Query("DELETE FROM service_requests WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<ServiceRequest>)
}