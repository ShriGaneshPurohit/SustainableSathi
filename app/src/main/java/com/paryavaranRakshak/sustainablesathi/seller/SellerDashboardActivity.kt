package com.paryavaranRakshak.sustainablesathi.seller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.firebase.auth.FirebaseAuth
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.SplashScreen
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySellerDashboardBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper
import com.paryavaranRakshak.sustainablesathi.seller.fragments.FacilityLocatorFragment
import com.paryavaranRakshak.sustainablesathi.seller.fragments.GyaanKoshFragment
import com.paryavaranRakshak.sustainablesathi.seller.fragments.HomeFragment
import java.io.File

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

        //E - Waste Facility Locator
        //binding.cvEWasteLocator.setOnClickListener{ startActivity(Intent(this,
          //  FacilityLocatorActivity::class.java)) }

        // Sell E - Waste
        //binding.cvSellEWaste.setOnClickListener{ startActivity(Intent(this, SellEWasteActivity::class.java)) }

        //Knowledge Hub
        //binding.cvKnowledgeHub.setOnClickListener{ startActivity(Intent(this, KnowledgeHubActivity::class.java)) }

        //logout
        //binding.ivLogo.setOnClickListener { exit() }

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
            }
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