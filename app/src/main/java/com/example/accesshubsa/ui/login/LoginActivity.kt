package com.example.accesshubsa.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.accesshubsa.data.remote.LoginRequest
import com.example.accesshubsa.data.remote.RetrofitClient
import com.example.accesshubsa.databinding.ActivityLoginBinding
import com.example.accesshubsa.ui.home.HomeActivity
import com.example.accesshubsa.ui.register.RegisterActivity
import com.example.accesshubsa.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { login() }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnGoogle.setOnClickListener {
            Toast.makeText(
                this,
                "Google SSO must be configured with your Firebase/Google project.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter a valid email"
            return
        }

        if (password.length < 8) {
            binding.etPassword.error = "Minimum 8 characters"
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.create(this@LoginActivity).login(
                    LoginRequest(email, password)
                )

                if (response.isSuccessful) {
                    val body = response.body()!!
                    SessionManager(this@LoginActivity).saveLogin(body.token, body.userId, body.firstName, body.surname, body.email)

                    startActivity(
                        Intent(this@LoginActivity, HomeActivity::class.java)
                            .putExtra("firstName", body.firstName)
                    )
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Incorrect email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
                    "Could not connect to the API",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
