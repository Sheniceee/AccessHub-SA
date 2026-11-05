package com.example.accesshubsa.ui.services

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accesshubsa.data.remote.RetrofitClient
import com.example.accesshubsa.databinding.ActivityServicesBinding
import com.example.accesshubsa.ui.request.RequestActivity
import kotlinx.coroutines.launch

class ServicesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServicesBinding
    private lateinit var adapter: ServiceAdapter
    private var all = emptyList<com.example.accesshubsa.data.remote.ServiceResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("category")
        val search = intent.getStringExtra("searchQuery")
        binding.tvCategory.text = category ?: "All Services"

        adapter = ServiceAdapter(emptyList()) { service ->
            startActivity(Intent(this, RequestActivity::class.java).apply {
                putExtra("serviceId", service.serviceId)
                putExtra("serviceName", service.serviceName)
                putExtra("description", service.description)
            })
        }
        binding.recyclerServices.layoutManager = LinearLayoutManager(this)
        binding.recyclerServices.adapter = adapter

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.create(this@ServicesActivity).getServices()
                if (response.isSuccessful) {
                    all = response.body().orEmpty()
                    var shown = all
                    if (!category.isNullOrBlank())
                        shown = shown.filter { it.category.equals(category, true) }
                    if (!search.isNullOrBlank())
                        shown = shown.filter { it.serviceName.contains(search, true) }
                    adapter.update(shown)
                }
            } catch (_: Exception) {
                Toast.makeText(this@ServicesActivity, "Unable to load services.", Toast.LENGTH_LONG).show()
            }
        }

        binding.etServiceSearch.setOnEditorActionListener { _, _, _ ->
            val q = binding.etServiceSearch.text.toString().trim()
            adapter.update(
                if (q.isEmpty()) all else all.filter {
                    it.serviceName.contains(q, true) ||
                    it.category.contains(q, true) ||
                    it.description.contains(q, true)
                }
            )
            true
        }
    }
}
