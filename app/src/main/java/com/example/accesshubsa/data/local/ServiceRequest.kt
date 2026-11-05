package com.example.accesshubsa.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_requests")
data class ServiceRequest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val serviceId: Int,
    val description: String,
    val priority: String,
    val status: String = "Pending Sync",
    val synced: Boolean = false
)
