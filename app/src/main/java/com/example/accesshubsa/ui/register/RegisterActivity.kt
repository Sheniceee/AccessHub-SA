package com.example.accesshubsa.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.accesshubsa.data.remote.RegisterRequest
import com.example.accesshubsa.data.remote.RetrofitClient
import com.example.accesshubsa.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener { register() }
    }

    private fun register() {
        val first = binding.etFirstName.text.toString().trim()
        val surname = binding.etSurname.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirm = binding.etConfirmPassword.text.toString()

        if (first.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Complete all required fields.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter a valid email"
            return
        }
        if (password.length < 8) {
            binding.etPassword.error = "Minimum 8 characters"
            return
        }
        if (password != confirm) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.create(this@RegisterActivity).register(
                    RegisterRequest(first, surname, email, phone, password)
                )
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration successful. Please log in.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration failed: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Could not connect to the API",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
