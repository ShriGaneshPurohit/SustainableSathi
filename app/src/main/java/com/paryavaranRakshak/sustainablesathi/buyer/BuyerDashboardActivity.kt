package com.paryavaranRakshak.sustainablesathi.buyer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.paryavaranRakshak.sustainablesathi.SplashScreen
import com.paryavaranRakshak.sustainablesathi.databinding.ActivityBuyerDashboardBinding
import com.paryavaranRakshak.sustainablesathi.other.LoginSharedPreferenceHelper

class BuyerDashboardActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityBuyerDashboardBinding

    private lateinit var loginHelper: LoginSharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginHelper = LoginSharedPreferenceHelper(this)

        val adapter = ViewPagerAdapter(this)
        binding.viewpager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Near You"
                1 -> "Categories"
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()

        //logout
        binding.textView2.setOnClickListener { logout() }

    }

    private fun logout() {
        loginHelper.setLoginStatus("pending")
        loginHelper.setUid("none")
        loginHelper.setUserType("none")
        startActivity(Intent(this, SplashScreen::class.java))
        finishAffinity()
    }

}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2 // Number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NearYouFragment()
            1 -> CategoriesFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}