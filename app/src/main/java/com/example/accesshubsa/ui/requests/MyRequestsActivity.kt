package com.example.accesshubsa.ui.requests

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accesshubsa.data.local.AccessHubDatabase
import com.example.accesshubsa.data.local.ServiceRequest
import com.example.accesshubsa.data.remote.RetrofitClient
import com.example.accesshubsa.databinding.ActivityMyRequestsBinding
import kotlinx.coroutines.launch

class MyRequestsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RequestAdapter(emptyList())
        binding.recyclerRequests.layoutManager = LinearLayoutManager(this)
        binding.recyclerRequests.adapter = adapter

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.create(this@MyRequestsActivity).getRequests()
                if (response.isSuccessful) {
                    val serverItems = response.body().orEmpty().map {
                        ServiceRequest(
                            id = it.requestId.toLong(),
                            serviceId = it.serviceId,
                            description = it.description,
                            priority = it.priority,
                            status = it.status,
                            synced = true
                        )
                    }
                    val dao = AccessHubDatabase.getDatabase(applicationContext).requestDao()
                    dao.insertAll(serverItems)
                }
            } catch (_: Exception) { }

            AccessHubDatabase.getDatabase(applicationContext)
                .requestDao().getAllRequests().collect { adapter.update(it) }
        }
    }
}
