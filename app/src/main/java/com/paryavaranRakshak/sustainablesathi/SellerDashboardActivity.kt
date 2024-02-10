package com.paryavaranRakshak.sustainablesathi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySellerDashboardBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper

class SellerDashboardActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySellerDashboardBinding

    private lateinit var loginHelper: LoginSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginHelper = LoginSharedPreferenceHelper(this)

        //E - Waste Facility Locator
        binding.cvEWasteLocator.setOnClickListener{ startActivity(Intent(this,FacilityLocatorActivity::class.java)) }

        // Sell E - Waste
        binding.cvSellEWaste.setOnClickListener{ startActivity(Intent(this,SellEWasteActivity::class.java)) }

        //Knowledge Hub
        binding.cvKnowledgeHub.setOnClickListener{ startActivity(Intent(this,KnowledgeHubActivity::class.java)) }

        //logout
        binding.ivLogo.setOnClickListener { logout() }

    }

    private fun logout() {
        loginHelper.setLoginStatus("pending")
        loginHelper.setUid("none")
        loginHelper.setUserType("none")
        startActivity(Intent(this,SplashScreen::class.java))
        finishAffinity()
    }

}