package com.example.accesshubsa.ui.settings

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import android.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import com.example.accesshubsa.utils.SessionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.accesshubsa.data.remote.RetrofitClient
import com.example.accesshubsa.data.remote.UpdateProfileRequest
import com.example.accesshubsa.databinding.ActivitySettingsBinding
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinnerLanguage.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("English", "isiZulu", "Sesotho")
        )

        loadProfile()
        binding.btnSaveSettings.setOnClickListener { saveProfile() }

        binding.btnChangePassword.setOnClickListener { showChangePasswordDialog() }
        binding.btnDeleteAccount.setOnClickListener { confirmDeleteAccount() }
    }

    private fun loadProfile() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.create(this@SettingsActivity).getProfile()
                if (response.isSuccessful) {
                    val p = response.body()!!
                    binding.etFirstName.setText(p.firstName)
                    binding.etSurname.setText(p.surname)
                    binding.etEmail.setText(p.email)
                    binding.etPhone.setText(p.phoneNumber)
                    binding.spinnerLanguage.setSelection(
                        listOf("English", "isiZulu", "Sesotho").indexOf(p.preferredLanguage).coerceAtLeast(0)
                    )
                    binding.switchNotifications.isChecked = p.notificationEnabled
                }
            } catch (_: Exception) {}
        }
    }

    private fun saveProfile() {
        lifecycleScope.launch {
            try {
                val request = UpdateProfileRequest(
                    binding.etFirstName.text.toString().trim(),
                    binding.etSurname.text.toString().trim(),
                    binding.etPhone.text.toString().trim(),
                    binding.spinnerLanguage.selectedItem.toString(),
                    binding.switchNotifications.isChecked
                )
                val response = RetrofitClient.create(this@SettingsActivity).updateProfile(request)
                Toast.makeText(
                    this@SettingsActivity,
                    if (response.isSuccessful) "Settings saved." else "Could not save settings.",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (_: Exception) {
                Toast.makeText(this@SettingsActivity, "API unavailable.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showChangePasswordDialog() {
        val box = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(40, 10, 40, 0) }
        val current = EditText(this).apply { hint = "Current password"; inputType = 0x81 }
        val next = EditText(this).apply { hint = "New password (8+ characters)"; inputType = 0x81 }
        box.addView(current); box.addView(next)
        AlertDialog.Builder(this).setTitle("Change Password").setView(box)
            .setNegativeButton("Cancel", null).setPositiveButton("Change") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val response = RetrofitClient.create(this@SettingsActivity).changePassword(
                            com.example.accesshubsa.data.remote.ChangePasswordRequest(current.text.toString(), next.text.toString())
                        )
                        Toast.makeText(this@SettingsActivity, if (response.isSuccessful) "Password changed." else "Could not change password.", Toast.LENGTH_LONG).show()
                    } catch (_: Exception) { Toast.makeText(this@SettingsActivity, "API unavailable.", Toast.LENGTH_SHORT).show() }
                }
            }.show()
    }

    private fun confirmDeleteAccount() {
        AlertDialog.Builder(this).setTitle("Delete account?")
            .setMessage("This permanently deletes your AccessHub SA account and cannot be undone.")
            .setNegativeButton("Cancel", null).setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val response = RetrofitClient.create(this@SettingsActivity).deleteAccount()
                        if (response.isSuccessful) {
                            SessionManager(this@SettingsActivity).clear()
                            Toast.makeText(this@SettingsActivity, "Account deleted.", Toast.LENGTH_LONG).show()
                            finishAffinity()
                        } else Toast.makeText(this@SettingsActivity, "Could not delete account.", Toast.LENGTH_LONG).show()
                    } catch (_: Exception) { Toast.makeText(this@SettingsActivity, "API unavailable.", Toast.LENGTH_SHORT).show() }
                }
            }.show()
    }
}
