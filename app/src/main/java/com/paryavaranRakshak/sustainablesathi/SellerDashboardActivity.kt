package com.paryavaranRakshak.sustainablesathi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.paryavaranRakshak.sustainablesathi.databinding.ActivitySellerDashboardBinding

class SellerDashboardActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivitySellerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //E - Waste Facility Locator
        binding.cvEWasteLocator.setOnClickListener{ startActivity(Intent(this,FacilityLocatorActivity::class.java)) }

        // Sell E - Waste
        binding.cvSellEWaste.setOnClickListener{ startActivity(Intent(this,SellEWasteActivity::class.java)) }

        //Knowledge Hub
        binding.cvKnowledgeHub.setOnClickListener{ startActivity(Intent(this,KnowledgeHubActivity::class.java)) }

    }
}