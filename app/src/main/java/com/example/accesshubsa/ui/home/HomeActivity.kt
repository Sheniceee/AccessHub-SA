package com.example.accesshubsa.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.accesshubsa.databinding.ActivityHomeBinding
import com.example.accesshubsa.ui.login.LoginActivity
import com.example.accesshubsa.ui.requests.MyRequestsActivity
import com.example.accesshubsa.ui.services.ServicesActivity
import com.example.accesshubsa.ui.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstName = intent.getStringExtra("firstName") ?: "User"
        binding.tvWelcome.text = "Welcome, $firstName"

        binding.btnHealth.setOnClickListener { openServices("Health") }
        binding.btnEducation.setOnClickListener { openServices("Education") }
        binding.btnCommunity.setOnClickListener { openServices("Community") }
        binding.btnGovernment.setOnClickListener { openServices("Government") }

        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                startActivity(Intent(this, ServicesActivity::class.java).putExtra("searchQuery", query))
            } else {
                Toast.makeText(this, "Enter a service to search", Toast.LENGTH_SHORT).show()
            }
            true
        }

        binding.btnMyRequests.setOnClickListener {
            startActivity(Intent(this, MyRequestsActivity::class.java))
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            getSharedPreferences("session", MODE_PRIVATE).edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }

    private fun openServices(category: String) {
        startActivity(Intent(this, ServicesActivity::class.java).putExtra("category", category))
    }
}
