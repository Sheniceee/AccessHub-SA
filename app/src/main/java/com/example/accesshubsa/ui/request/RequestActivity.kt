package com.example.accesshubsa.ui.request

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.accesshubsa.data.local.AccessHubDatabase
import com.example.accesshubsa.data.local.ServiceRequest
import com.example.accesshubsa.data.remote.CreateRequest
import com.example.accesshubsa.data.remote.RetrofitClient
import com.example.accesshubsa.databinding.ActivityRequestBinding
import kotlinx.coroutines.launch
import com.example.accesshubsa.sync.RequestSyncWorker

class RequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceId = intent.getIntExtra("serviceId", 0)
        binding.tvSelectedService.text = intent.getStringExtra("serviceName") ?: "Service"
        binding.tvServiceDescription.text = intent.getStringExtra("description") ?: ""

        binding.spinnerPriority.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Low", "Medium", "High", "Emergency")
        )

        binding.btnSubmitRequest.setOnClickListener {
            submit(serviceId)
        }
    }

    private fun submit(serviceId: Int) {
        val description = binding.etRequestDescription.text.toString().trim()
        val priority = binding.spinnerPriority.selectedItem.toString()

        if (description.isEmpty()) {
            binding.etRequestDescription.error = "Describe your request"
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.create(this@RequestActivity).createRequest(
                    CreateRequest(serviceId, description, priority)
                )

                if (response.isSuccessful) {
                    Toast.makeText(this@RequestActivity, "Request submitted.", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    saveOffline(serviceId, description, priority)
                }
            } catch (_: Exception) {
                saveOffline(serviceId, description, priority)
            }
        }
    }

    private suspend fun saveOffline(serviceId: Int, description: String, priority: String) {
        AccessHubDatabase.getDatabase(applicationContext).requestDao().insert(
            ServiceRequest(
                id = -System.currentTimeMillis(),
                serviceId = serviceId,
                description = description,
                priority = priority
            )
        )
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        WorkManager.getInstance(applicationContext).enqueue(
            OneTimeWorkRequestBuilder<RequestSyncWorker>().setConstraints(constraints).build()
        )
        Toast.makeText(
            this,
            "Offline: request saved. It will sync automatically when internet returns.",
            Toast.LENGTH_LONG
        ).show()
        finish()
    }
}
