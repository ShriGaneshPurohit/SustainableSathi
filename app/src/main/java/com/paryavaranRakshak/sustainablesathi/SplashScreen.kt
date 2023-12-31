package com.paryavaranRakshak.sustainablesathi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Seller_DashBoard::class.java))
            overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
            finishAffinity()
        }, 1000.toLong())
    }
}