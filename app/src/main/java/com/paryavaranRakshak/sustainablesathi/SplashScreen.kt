package com.paryavaranRakshak.sustainablesathi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySplashScreenBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper

class SplashScreen : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySplashScreenBinding

    //splash time
    private val Splash_Time_Out = 1000

    //firebase authentication
    private lateinit var auth: FirebaseAuth

    //shared pref
    private lateinit var loginHelper: LoginSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferencesHelper
        loginHelper = LoginSharedPreferenceHelper(this)

        //login
        auth = FirebaseAuth.getInstance()

        // If onboarding status is null, show the OnBoarding
        if (loginHelper.getLoginStatus() == "completed") {
            if (loginHelper.getUserType() == "seller") {
                sellerLogin()
            } else {
                buyerLogin()
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, BuyerDashboardActivity::class.java))
                overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
                finishAffinity()
            }, Splash_Time_Out.toLong())
        }

    }

    private fun buyerLogin() {
        // Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, BuyerDashboardActivity::class.java))
            overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
            finishAffinity()
        }, Splash_Time_Out.toLong())
    }

    private fun sellerLogin() {
        // Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SellerDashboardActivity::class.java))
            overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
            finishAffinity()
        }, Splash_Time_Out.toLong())
    }

}