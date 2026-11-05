package com.example.accesshubsa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(request: ServiceRequest)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<ServiceRequest>)

    @Query("SELECT * FROM service_requests ORDER BY id DESC")
    fun getAll(): Flow<List<ServiceRequest>>

    @Query("SELECT * FROM service_requests WHERE synced = 0")
    suspend fun getPending(): List<ServiceRequest>

    @Query("UPDATE service_requests SET synced = 1, status = 'Submitted' WHERE id = :id")
    suspend fun markAsSynced(id: Long)
}
