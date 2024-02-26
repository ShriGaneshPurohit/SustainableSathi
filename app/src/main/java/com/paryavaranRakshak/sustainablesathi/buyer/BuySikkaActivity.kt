package com.paryavaranRakshak.sustainablesathi.buyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityBuySikkaBinding

class BuySikkaActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityBuySikkaBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuySikkaBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}