package com.paryavaranRakshak.sustainablesathi.seller

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.SplashScreen
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySellerDashboardBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import com.paryavaranRakshak.sustainablesathi.seller.fragments.FacilityLocatorFragment
import com.paryavaranRakshak.sustainablesathi.seller.fragments.GyaanKoshFragment
import com.paryavaranRakshak.sustainablesathi.seller.fragments.HomeFragment


class SellerDashboardActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySellerDashboardBinding

    private lateinit var loginHelper: LoginSharedPreferenceHelper

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginHelper = LoginSharedPreferenceHelper(this)

        binding.ivSikka.setOnClickListener { startActivity(Intent(this,RedeemSikkaActivity::class.java)) }

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.toString()) {
                "Home" -> {
                    replaceFragment(HomeFragment())
                }
                "Locator" -> {
                    replaceFragment(FacilityLocatorFragment())
                }
                "Sell" -> {
                    startActivity(Intent(this,SellEWasteActivity::class.java))
                }
                "GyaanKosh" -> {
                    replaceFragment(GyaanKoshFragment())
                }
                "Policy" -> {
                    val externalLink = "https://cpcb.nic.in/uploads/Projects/E-Waste/e-waste_rules_2022.pdf"

                    // Create an intent with ACTION_VIEW and the external link URI

                    // Create an intent with ACTION_VIEW and the external link URI
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(externalLink))

                    // Start the intent

                    // Start the intent
                    startActivity(intent)

                    // Finish the current activity if needed

                    // Finish the current activity if needed

                }}
            return@setOnItemSelectedListener true
        }

    }

    private fun exit() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        loginHelper.setLoginStatus("pending")
        loginHelper.setUid("none")
        loginHelper.setUserType("none")
        startActivity(Intent(this, SplashScreen::class.java))
        finishAffinity()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}