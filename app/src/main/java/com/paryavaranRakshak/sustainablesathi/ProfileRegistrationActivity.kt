package com.paryavaranRakshak.sustainablesathi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityProfileRegistrationBinding

class ProfileRegistrationActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfileRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}